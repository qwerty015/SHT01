package com.autohubtraining.autohub.scene.bestimages;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class BestImagesPresenter implements BestImagesContract.Presenter {
    private BestImagesContract.View view;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> aldownloadFileUrl = new ArrayList<>();


    public BestImagesPresenter(BestImagesContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked(HashMap<String, String> hm) {

        view.showLoading();
        //view.navigateToNextScreen();

        String image1 = hm.get("image1");
        if (image1 != null && image1.length() > 0)
            al.add(image1);

        String image2 = hm.get("image2");
        if (image2 != null && image2.length() > 0)
            al.add(image2);


        String image3 = hm.get("image3");
        if (image3 != null && image3.length() > 0)
            al.add(image3);

        String image4 = hm.get("image4");

        if (image4 != null && image4.length() > 0)
            al.add(image4);


        for (int i = 0; i < al.size(); i++) {
            uploadImage(new File(al.get(i)));
        }

        if (al.size() == 0) {

            saveDataIntoFireStore();
        }


    }


    void uploadImage(File file) {


        Uri uri = Uri.fromFile(file);

        StorageReference imgRef = storageRef.child("images/" + uri.getLastPathSegment());
        UploadTask uploadTask = imgRef.putFile(uri);

        // Register observers to listen for when the download is done or if it fails

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("profile_url", exception.getMessage());

                view.hideLoading();

                view.showError(exception.getMessage());
                //view.hideLoading();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //view.hideLoading();
                Log.e("workinggg", "wokimggg");
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        aldownloadFileUrl.add(uri.toString());
                        Log.e("alDownwnw", aldownloadFileUrl.size() + " " + al.size());
                        if (aldownloadFileUrl.size() == al.size()) {

                            saveDataIntoFireStore();
                        }

                        Log.d("downloadUrl", "onSuccess: uri= " + uri.toString());


                    }
                });


            }
        });

    }


    void saveDataIntoFireStore() {

        User user = DataHandler.getInstance().getUser();
        user.setBestImages(aldownloadFileUrl);

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                /* save data */
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


    }

}
