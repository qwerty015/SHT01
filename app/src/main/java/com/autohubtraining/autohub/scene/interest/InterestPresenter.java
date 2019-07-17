package com.autohubtraining.autohub.scene.interest;


import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class InterestPresenter implements InterestContract.Presenter {
    private InterestContract.View view;

    public InterestPresenter(InterestContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked(ArrayList<String> alUserInterest) {





        User user = DataHandler.getInstance().getUser();

        user.setUserInterests(alUserInterest);

        /* set data into firebase database*/
        //FirebaseDatabase.getInstance().getReference(AppConstants.userRef).child(user.getUserId()).setValue(user);
        /* save data */

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        view.showLoading();


        db.collection(AppConstants.userRef).document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                //Log.e("firestore", "data failed with an exception" + e.toString());
            }
        });

       // DataHandler.getInstance().setUser(user);




    }

}
