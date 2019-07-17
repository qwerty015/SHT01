package com.autohubtraining.autohub.scene.viewmore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ViewMorePresenter implements ViewMoreContract.ViewMorePresenter {
    ViewMoreContract.ViewMoreView view;


    public ViewMorePresenter(ViewMoreActivity viewMoreActivity) {
        this.view = viewMoreActivity;
    }

    @Override
    public void onCreate() {
        view.addTabLayout();
    }

    @Override
    public void onDestroy() {

    }



    @Override
    public void onNextButtonClicked() {

    }

    public void setFavourite(UserData user, boolean isFavourite) {
        //view.showLoading();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        UserData currentUserData = DataHandler.getInstance().getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> likeMap = new HashMap<>();

        likeMap.put("name", user.getFirstName());
        likeMap.put("id", user.getUserId());
        likeMap.put("is_favourite", isFavourite);
        map.put(user.getUserId(), likeMap);




        if (isFavourite) {
            db.collection(AppConstants.userRef).document(currentUserData.getUserId()).collection(AppConstants.favourite_ref).document(user.getUserId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // view.hideLoading();
                    view.isFavouriteSuccessfully(isFavourite);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //view.hideLoading();
                    view.showError(e.toString());
                    Log.e("firestore", "data failed with an exception" + e.toString());
                }
            });
        } else {
            db.collection(AppConstants.userRef).document(currentUserData.getUserId()).collection(AppConstants.favourite_ref).document(user.getUserId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // view.hideLoading();
                    //view.navigateToNextScreen();
                    view.isFavouriteSuccessfully(isFavourite);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //view.hideLoading();
                    view.showError(e.toString());
                    Log.e("firestore", "data failed with an exception" + e.toString());
                }
            });
        }
    }
}
