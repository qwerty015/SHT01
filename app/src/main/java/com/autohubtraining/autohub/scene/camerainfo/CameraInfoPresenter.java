package com.autohubtraining.autohub.scene.camerainfo;


import android.util.Log;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

public class CameraInfoPresenter implements CameraInfoContract.Presenter {
    private CameraInfoContract.View view;

    public CameraInfoPresenter(CameraInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked(String bio) {

        User user = DataHandler.getInstance().getUser();
        user.setBio(bio);

        /* set data into firebase database*/



        /*sending user info to firestore*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        view.showLoading();
        db.collection(AppConstants.ref_user).document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                view.hideLoading();
                DataHandler.getInstance().setUser(user);
                view.navigateToNextScreen();

                // Log.e("firestore", "data successfully added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideLoading();
                view.showError(e.toString());
                Log.e("firestore", "data failed with an exception" + e.toString());
            }
        });


    }

}
