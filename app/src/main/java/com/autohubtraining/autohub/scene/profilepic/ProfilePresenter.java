package com.autohubtraining.autohub.scene.profilepic;


import android.net.Uri;

import androidx.annotation.NonNull;

import android.util.Log;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View view;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        storageRef = storage.getReference();
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked() {
        view.navigateToNextScreen();
    }

    @Override
    public void uploadPic(File file) {
        view.showLoading();
        if (file.length() > 0) {

            Uri uri = Uri.fromFile(file);

            StorageReference riversRef = storageRef.child("images/" + uri.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(uri);

            // Register observers to listen for when the download is done or if it fails


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                     Log.e("profile_url", exception.getMessage());

                    view.showError(exception.getMessage());
                    view.hideLoading();
                    saveDataIntoFireStore("");


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // String downUri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //aldownloadFileUrl.add(uri.toString());


                            saveDataIntoFireStore(uri.toString());
                            Log.d("downloadUrl", "onSuccess: uri= " + uri.toString());


                        }
                    });


                }
            });
        } else {
            saveDataIntoFireStore("");

        }
    }

    void saveDataIntoFireStore(String profilePicUrl) {
        view.hideLoading();
        User user = DataHandler.getInstance().getUser();
        user.setPictureUrl(profilePicUrl);
        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.userRef).document(user.getUserId()).set(user);
        /* save data */
        DataHandler.getInstance().setUser(user);
        UserData userData = new UserData();
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setBio(user.getBio());
        userData.setPhoneNo(user.getPhoneNo());
        userData.setBestImages(user.getBestImages());
        userData.setPictureUrl(user.getPictureUrl());
        userData.setCountryCode(user.getCountryCode());
        userData.setUserId(user.getUserId());
        userData.setUserInterests(user.getUserInterests());
        userData.setType(user.getType());
        userData.setSignupStatus(user.getSignupStatus());
        DataHandler.getInstance().setCurrentUser(userData);
        onNextBtnClicked();
    }


}
