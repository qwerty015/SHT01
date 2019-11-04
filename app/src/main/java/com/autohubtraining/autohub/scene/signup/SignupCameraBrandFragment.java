package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.CameraBrand;
import com.autohubtraining.autohub.data.model.public_data.CameraModel;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.signup.custom.EquipmentAdapter;
import com.autohubtraining.autohub.scene.signup.custom.CameraModelAdapter;
import com.autohubtraining.autohub.scene.signup.custom.CameraBrandAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupCameraBrandFragment extends BaseFragment {

    @BindView(R.id.spBrand)
    Spinner spBrand;
    @BindView(R.id.spModel)
    Spinner spModel;
    @BindView(R.id.lvEquipment)
    ListView lvEquipment;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;

    SignupActivity activity;

    ArrayList<String> alEquipments = new ArrayList<>();

    CameraBrand selectedCameraBrand;
    CameraModel selectedCameraModel;

    EquipmentAdapter equipmentAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_camera_brand, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();

        /* setting adapter of equipments*/
        alEquipments.add("");
        equipmentAdapter = new EquipmentAdapter(activity, alEquipments,true);
        lvEquipment.setAdapter(equipmentAdapter);

        getCameraBrands();

        return retView;
    }

    @OnClick({R.id.nextBtn, R.id.ivAdd})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                saveCameraData();

                break;
            case R.id.ivAdd:
                alEquipments.add("");
                equipmentAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lvEquipment);

                break;
        }
    }

    /**
     * method is used for uploading camera datas to FirebaseFirestore.
     *
     * @param
     * @return
     */
    public void saveCameraData() {
        showLoading("");

        User user = DataHandler.getInstance().getUser();

        UserCameraResponse userCameraResponse = new UserCameraResponse();

        if (selectedCameraBrand != null) {
            userCameraResponse.setCameraBrandId(selectedCameraBrand.getBrandId());
            userCameraResponse.setCameraBrand(selectedCameraBrand.getBrandName());
        }

        if (selectedCameraModel != null) {
            userCameraResponse.setCameraModelId(selectedCameraModel.getModelId());
            userCameraResponse.setCameraModel(selectedCameraModel.getModelName());
        }

        userCameraResponse.setCameraAccessories(equipmentAdapter.getEquipment());

        FirebaseFirestore.getInstance().collection(AppConstants.ref_camera).document(user.getUserId()).set(userCameraResponse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                DataHandler.getInstance().setUser(user);

                UserData userData = DataHandler.getInstance().getCurrentUser();
                userData.setUserCamera(userCameraResponse);

                DataHandler.getInstance().setCurrentUser(userData);

                activity.setViewPager(activity.nCurrentPageIndex + 1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                showErrorToast(e.toString());
            }
        });
    }

    /**
     * method is used for getting camera brands from FirebaseFirestore.
     *
     * @param
     * @return
     */
    public void getCameraBrands() {
        FirebaseFirestore.getInstance().collection(AppConstants.ref_public_data).document("camera_brands").addSnapshotListener((documentSnapshot, e) -> {
            Map<String, Object> map = documentSnapshot.getData();
            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();

            ArrayList<CameraBrand> alBrands = new ArrayList<>();

            while (itr.hasNext()) {
                HashMap<String, String> brand = (HashMap<String, String>) itr.next().getValue();

                CameraBrand cameraBrand = new CameraBrand();
                cameraBrand.setBrandId(brand.get("brandId"));
                cameraBrand.setBrandName(brand.get("brandName"));

                alBrands.add(cameraBrand);
            }

            CameraBrandAdapter brandAdapter = new CameraBrandAdapter(activity.getApplicationContext(), alBrands);
            spBrand.setAdapter(brandAdapter);
            spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCameraBrand = alBrands.get(position);
                    getCameraModels(selectedCameraBrand.getBrandId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        });
    }

    /**
     * method is used for getting camera models from FirebaseFirestore.
     *
     * @param brandId
     * @return
     */
    public void getCameraModels(String brandId) {
        FirebaseFirestore.getInstance().collection(AppConstants.ref_public_data).document("camera_models").addSnapshotListener((documentSnapshot, e) -> {
            Map<String, Object> map = documentSnapshot.getData();
            map = (Map<String, Object>) map.get(brandId);

            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();

            ArrayList<CameraModel> alModels = new ArrayList<>();

            while (itr.hasNext()) {
                HashMap<String, String> brand = (HashMap<String, String>) itr.next().getValue();

                CameraModel cameraModel = new CameraModel();
                cameraModel.setModelId(brand.get("modelId"));
                cameraModel.setModelName(brand.get("modelName"));

                alModels.add(cameraModel);

            }

            CameraModelAdapter cameraModelAdapter = new CameraModelAdapter(activity.getApplicationContext(), alModels);
            spModel.setAdapter(cameraModelAdapter);
            spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCameraModel = alModels.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        });
    }
}
