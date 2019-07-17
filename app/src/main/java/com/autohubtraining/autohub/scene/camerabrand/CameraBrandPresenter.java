package com.autohubtraining.autohub.scene.camerabrand;


import androidx.annotation.NonNull;

import android.util.Log;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.CameraBrand;
import com.autohubtraining.autohub.data.model.public_data.CameraModel;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CameraBrandPresenter implements CameraBrandContract.Presenter {
    private CameraBrandContract.View view;
    FirebaseDatabase dbRef = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String[] alBrands = {"Canon", "Sony"};


    public CameraBrandPresenter(CameraBrandContract.View view) {
        this.view = view;


    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked(CameraBrand selectedCameraBrand, CameraModel selectedCameraModel, ArrayList<String> equipment) {


        view.showLoading();
        User user = DataHandler.getInstance().getUser();

        UserCameraResponse userCameraResponse = new UserCameraResponse();

        if (selectedCameraBrand != null) {
            userCameraResponse.setCameraBrand(selectedCameraBrand.getBrandName());
            userCameraResponse.setCameraBrandId(selectedCameraBrand.getBrandId());
        }
        if (selectedCameraModel != null) {
            userCameraResponse.setCameraModel(selectedCameraModel.getModelName());
            userCameraResponse.setCameraModelId(selectedCameraModel.getModelId());
        }

        userCameraResponse.setCameraAccessories(equipment);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.cameraRef).document(user.getUserId()).set(userCameraResponse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                view.hideLoading();
                DataHandler.getInstance().setUser(user);


                UserData userData = DataHandler.getInstance().getCurrentUser();
                userData.setUserCamera(userCameraResponse);

                DataHandler.getInstance().setCurrentUser(userData);


                view.navigateToNextScreen();

                // Log.e("firestore", "data successfully added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                view.hideLoading();
                view.showError(e.toString());
                //Log.e("firestore", "data failed with an exception" + e.toString());
            }
        });
    }

    @Override
    public void getCameraBrands() {


//        dbRef.getReference("public_data").child("camera_brands").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                ArrayList<CameraBrand> alBrands = new ArrayList<>();
//
//                if (dataSnapshot.exists()) {
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//
//                        CameraBrand camerasResponse = snapshot.getValue(CameraBrand.class);
//                        alBrands.add(camerasResponse);
//
//
//                    }
//                    view.getCameraBrands(alBrands);
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //DocumentReference cities = db.collection("dummyyyy").document("camera_brands");
        db.collection(AppConstants.public_data).document("camera_brands").addSnapshotListener((documentSnapshot, e) -> {


            Map<String, Object> map = documentSnapshot.getData();

            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            //please check
            ArrayList<CameraBrand> alBrands = new ArrayList<>();

            while (itr.hasNext()) {
                HashMap<String, String> brand = (HashMap<String, String>) itr.next().getValue();
                CameraBrand cameraBrand = new CameraBrand();
                cameraBrand.setBrandName(brand.get("brandName"));
                cameraBrand.setBrandId(brand.get("brandId"));
                alBrands.add(cameraBrand);

            }
            view.getCameraBrands(alBrands);


        });


    }


    @Override
    public void postBrand() {


        //ArrayList<CameraBrand> brandData = new ArrayList<>();


    }

    @Override
    public void getModels(String brandId) {


//        Query query = dbRef.getReference("public_data").child("camera_models").orderByKey().equalTo(brandId);
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("brandndnd", dataSnapshot.getChildrenCount() + "");
//
//                ArrayList<CameraModel> alBrands = new ArrayList<>();
//                if (dataSnapshot.exists()) {
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//
//                            CameraModel camerasResponse = snapshot1.getValue(CameraModel.class);
//                            //Log.e("brandndndn", camerasResponse.getCameraBrandId());
//
//                            alBrands.add(camerasResponse);
//                        }
//
//
//                        //Log.e("cameraResponse", camerasResponse.getCameraBrand());
//
//
//                    }
//                    view.getCameraModels(alBrands);
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //DocumentReference cities = db.collection("dummyyyy").document("camera_brands");
        db.collection(AppConstants.public_data).document("camera_models").addSnapshotListener((documentSnapshot, e) -> {

            Map<String, Object> map = documentSnapshot.getData();
            map = (Map<String, Object>) map.get(brandId);

            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            ArrayList<CameraModel> alBrands = new ArrayList<>();

            //Log.e("bradddd", brandId.toString());
            while (itr.hasNext()) {
                HashMap<String, String> brand = (HashMap<String, String>) itr.next().getValue();
                CameraModel cameraBrand = new CameraModel();
                cameraBrand.setModelId(brand.get("modelId"));
                cameraBrand.setModelName(brand.get("modelName"));
//                Log.e("brandddd", itr.next().getValue().toString());
                alBrands.add(cameraBrand);

            }
            view.getCameraModels(alBrands);


        });


    }

}
