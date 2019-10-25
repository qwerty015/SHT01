package com.autohubtraining.autohub.scene.camerabrand;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.model.public_data.CameraBrand;
import com.autohubtraining.autohub.data.model.public_data.CameraModel;
import com.autohubtraining.autohub.scene.SignupBaseActivity;
import com.autohubtraining.autohub.scene.SignupBaseActivity;
import com.autohubtraining.autohub.scene.camerabrand.EquipmentAdapter.EquipmentAdapter;
import com.autohubtraining.autohub.scene.interest.InterestActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN6;

public class CameraBrandActivity extends SignupBaseActivity implements CameraBrandContract.View,EquipmentAdapter.ItemClick {

    private CameraBrandPresenter presenter;
    @BindView(R.id.spBrand)
    Spinner spBrand;
    @BindView(R.id.spModel)
    Spinner spModel;
    @BindView(R.id.lvEquipment)
    ListView lvEquipment;


    @BindView(R.id.ivAdd)
    ImageView ivAdd;


    ArrayList<CameraModel> alModels = new ArrayList<>();
    ArrayList<String> alEquipments = new ArrayList<>();
    CustomModelItem cameraModelAdapter;


    CameraModel selectedCameraModel;
    CameraBrand selectedCameraBrand;
    EquipmentAdapter equipmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_camera_brand);
        ButterKnife.bind(this);
        setProgressBar(SCREEN6);
        setup();
        /* setting adapter of cameras model*/
        cameraModelAdapter = new CustomModelItem(getApplicationContext(), alModels);
        spBrand.setAdapter(cameraModelAdapter);
        /* setting adapter of equipments*/
        alEquipments.add("");
        equipmentAdapter = new EquipmentAdapter(this, alEquipments,true);
        lvEquipment.setAdapter(equipmentAdapter);
        /* get camera brands*/
        presenter.getCameraBrands();
    }

    private void setup() {
        presenter = new CameraBrandPresenter(this);
    }


    @OnClick({R.id.nextBtn, R.id.ivAdd})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.onNextBtnClicked(selectedCameraBrand, selectedCameraModel, equipmentAdapter.getEquipment());
                break;
                case R.id.ivAdd:
                alEquipments.add("");
                equipmentAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lvEquipment);
                break;


        }
    }

    @Override
    public void navigateToNextScreen() {
        ArrayList<String> alEquipment = equipmentAdapter.getEquipment();
        Intent intent = new Intent(this, InterestActivity.class);
        startActivity(intent);
    }

    @Override
    public void getCameraBrands(final ArrayList<CameraBrand> al) {
        CameraBrandItem customAdapter = new CameraBrandItem(getApplicationContext(), al);
        spBrand.setAdapter(customAdapter);
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCameraBrand = al.get(position);
                presenter.getModels(al.get(position).getBrandId());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void getCameraModels(ArrayList<CameraModel> al) {
        this.alModels = al;
        cameraModelAdapter = new CustomModelItem(getApplicationContext(), al);
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


    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    public void showError(String error) {
        showErrorToast(error);
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void removeItem(int pos) {

    }
}