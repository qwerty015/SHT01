package com.autohubtraining.autohub.scene.finalscreen;


import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class LastPresenter implements LastContract.Presenter {

    private LastContract.View view;

    public LastPresenter(LastContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked(ArrayList<UserPlan> alPlans) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = DataHandler.getInstance().getUser();

        int index = 0;

        view.showLoading();

        for (UserPlan plan : alPlans) {


            //planId = db.collection(AppConstants.service_plan).document(user.getUserId()).collection("abcecccddd").getId();

            DocumentReference key = db.collection(AppConstants.service_plan).document();
            plan.setPlanId(key.getId());


            HashMap<String, Object> hm = new HashMap<>();
            hm.put(key.getId(), plan);
            db.collection(AppConstants.service_plan).document(user.getUserId()).set(hm, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    if (alPlans.size() == 2) {

                        view.hideLoading();
                        UserData userData = DataHandler.getInstance().getCurrentUser();
                        userData.setAlUserPlans(alPlans);
                        userData.setBestImages(user.getBestImages());
                        userData.setType(user.getType());
                        userData.setUserInterests(user.getUserInterests());
                        DataHandler.getInstance().setCurrentUser(userData);
                        view.navigateToNextScreen();


                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.e("rrorr", e.toString());
                    view.showError(e.toString());
                    view.hideLoading();
                }
            });

        }


        // view.navigateToNextScreen();
    }

    @Override
    public void onLoginBtnClicked() {

    }

}
