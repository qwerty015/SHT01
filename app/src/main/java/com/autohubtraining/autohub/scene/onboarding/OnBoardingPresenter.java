package com.autohubtraining.autohub.scene.onboarding;

import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OnBoardingPresenter implements OnBoardingContract.Presenter {
    private OnBoardingContract.View view;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public OnBoardingPresenter(OnBoardingContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public void isLogin() {
        checkUserIsLogin();
    }

    void postData() throws JSONException {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String[] alBrands = {"Canon", "Nikon", "Sony"};
        // String[] alModels1 = {"Canon", "Sony"};

        ArrayList<Map<String, String>> brandData = new ArrayList<>();
        for (int i = 0; i < alBrands.length; i++) {

            String id = db.collection(AppConstants.public_data).document().getId();

            Map<String, String> hashMap = new HashMap<>();

            hashMap.put("brandId", id + "");
            hashMap.put("brandName", alBrands[i]);

            HashMap jsonObject = new HashMap();
            jsonObject.put(id, hashMap);


            brandData.add(hashMap);
            db.collection(AppConstants.public_data).document("camera_brands").set(jsonObject, SetOptions.merge());

            ArrayList<String> alModel = new ArrayList<>();
            if (alBrands[i].equals("Canon")) {


                alModel = new ArrayList<>();
                alModel.add("EOS 3000D");
                alModel.add("EOS 1500D");
                alModel.add("EOS 1300D");
                alModel.add("EOS 200D");
                alModel.add("EOS 700D");
                alModel.add("EOS 77D");
                alModel.add("EOS 70D");
                alModel.add("EOS 70D MARK II");
                alModel.add("M50");
                alModel.add("EOS 800D");
                alModel.add("EOS M3");
                alModel.add("EOS M5");
                alModel.add("EOS 1200D");
                alModel.add("EOS 650D");
                alModel.add("EOS 750D");
                alModel.add("EOS 760D");
                alModel.add("EOS 2000D");
                alModel.add("EOS 100D");
                alModel.add("EOS 80D");
                alModel.add("EOS 70D");
                alModel.add("EOS 5D MARK IV");
                alModel.add("EOS 6D");
                alModel.add("EOS 6D MARK II");
                alModel.add("EOS 7D");
                alModel.add("EOS 7D MARK II");
                alModel.add("EOS RP");
                alModel.add("EOS R");
            } else if (alBrands[i].equals("Sony")) {

                alModel = new ArrayList<>();

                alModel.add("A5100");
                alModel.add("A6000");

                alModel.add("A68");
                alModel.add("A6300");
                alModel.add("A6400");
                alModel.add("A7");
                alModel.add("A6500");
                alModel.add("A7R");
                alModel.add("A7 II");
                alModel.add("A7S");
                alModel.add("A7R II");
                alModel.add("A7 III");
                alModel.add("A75 II");
                alModel.add("A99 II");
                alModel.add("A7R III");
                alModel.add("A9");


            } else {

                alModel.add("D3500");
                alModel.add("D3400");
                alModel.add("D5200");
                alModel.add("D5300");
                alModel.add("D5600");

                alModel.add("D3300");

                alModel.add("D3100");

                alModel.add("D7200");

                alModel.add("D7100");
                alModel.add("D3200");

                alModel.add("D500");

                alModel.add("D7500");
                alModel.add("D5500");
                alModel.add("D3000");
                alModel.add("D610");
                alModel.add("D5100");
                alModel.add("D750");
                alModel.add("D810");
                alModel.add("D800E");
                alModel.add("D850");
                alModel.add("D5");
                alModel.add("D4S");


            }

            Map<String, Object> modelMap = new HashMap<>();

            for (String str : alModel) {

                String modelId = db.collection(AppConstants.public_data).document().collection("cenera").document().getId();
                Map<String, String> modelHash = new HashMap<>();
                modelHash.put("modelId", modelId);
                modelHash.put("modelName", str);

                modelMap.put(modelId, modelHash);
            }


            HashMap jsonObject1 = new HashMap();
            jsonObject1.put(id, modelMap);

            db.collection(AppConstants.public_data).document("camera_models").set(jsonObject1, SetOptions.merge());


        }

        //Log.e("brandData", brandData.toString());


    }

    void checkUserIsLogin() {

        firebaseAuth = FirebaseAuth.getInstance();
        //Log.e("usreree", "insideeee");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {


            view.showLoading();

            //Log.e("usreree", user.getPhoneNumber().toString());
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference documentReference = db.collection(AppConstants.userRef).document(user.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        view.hideLoading();
                        DocumentSnapshot doc = task.getResult();

                        if (doc.getData() != null) {

                            UserData user = doc.toObject(UserData.class);

                            getCameraBrands(user);

                            //Log.e("userree", user.getPhoneNo());

                        }


                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.e("error", e.toString());
                            view.hideLoading();


                        }
                    });


        }


    }

    void getCameraBrands(UserData user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection(AppConstants.cameraRef).document(user.getUserId()).addSnapshotListener((documentSnapshot, e) -> {


            //Map<String, Object> map = documentSnapshot.getData();
            UserCameraResponse userCameraResponse = documentSnapshot.toObject(UserCameraResponse.class);
            // Log.e("camerResponse", userCameraResponse.getCameraAccessories().toString());


            user.setUserCamera(userCameraResponse);

            getServicePlans(user);


        });

    }


    void getServicePlans(UserData user) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.service_plan).document(user.getUserId()).addSnapshotListener((documentSnapshot, e) -> {

            ArrayList<UserPlan> alBrands = new ArrayList<>();
            Map<String, Object> map = documentSnapshot.getData();

            if (map != null) {

                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                //please check


                while (itr.hasNext()) {
                    HashMap<String, Object> brand = (HashMap<String, Object>) itr.next().getValue();

                    //UserPlan userPlan = (UserPlan) itr.next().getValue();


                    UserPlan userPlan = new UserPlan();
                    if (brand.get("planId") != null)
                        userPlan.setPlanId(brand.get("planId").toString());
                    if (brand.get("planName") != null)
                        userPlan.setPlanName(brand.get("planName").toString());
                    if (brand.get("numberOfPictures") != null)
                        userPlan.setNumberOfPictures(brand.get("numberOfPictures").toString());
                    if (brand.get("shootType") != null)
                        userPlan.setShootType(brand.get("shootType").toString());
                    if (brand.get("amount") != null)
                        userPlan.setAmount(brand.get("amount").toString());
                    if (brand.get("editingIncluded") != null)
                        userPlan.setEditingIncluded(brand.get("editingIncluded").toString());
                    alBrands.add(userPlan);
                    Log.e("usererere", userPlan.getPlanName());


                }
            }

            user.setAlUserPlans(alBrands);


            DataHandler.getInstance().setCurrentUser(user);
            view.navigateToHomeScreen();

        });
    }


    @Override
    public void onSignUpBtnClicked() {
        view.navigateToSignUp();
    }

    @Override
    public void onLoginBtnClicked() {
        view.navigateToLogin();
    }
}
