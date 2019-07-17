package com.autohubtraining.autohub.scene.editinfo;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;
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
import java.util.Map;

public class EditInfoPresenter implements EditInfoContract.Presenter {
    EditInfoContract.View view;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> aldownloadFileUrl = new ArrayList<>();
    ArrayList<String> alEquipments = new ArrayList<>();
    HashMap<String, String> hm = new HashMap<>();


    ArrayList<HashMap<String, String>> alTemp = new ArrayList<>();


    public EditInfoPresenter(EditInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void updateInfoButtonClick(HashMap<String, String> hm, String bio, ArrayList<String> equipment) {

        view.showLoading();
        this.alEquipments = equipment;
        this.hm = hm;


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
            uploadImage(new File(al.get(i)), bio);
        }

        if (al.size() == 0) {
            saveDataIntoFireStore(bio);
        }


    }


    void uploadImage(File file, String bio) {


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
                        if (aldownloadFileUrl.size() == al.size()) {

                            saveDataIntoFireStore(bio);
                        }

                        // Log.d("downloadUrl", "onSuccess: uri= " + uri.toString());


                    }
                });


            }
        });

    }

    void saveDataIntoFireStore(String bio) {

        /* update Bio and best images */
        UserData user = DataHandler.getInstance().getCurrentUser();
        //user.setBestImages(aldownloadFileUrl);

        if (user.getBestImages() != null) {


            for (String str : user.getBestImages()) {


                aldownloadFileUrl.add(str);
            }


        }


        Map<String, Object> data = new HashMap<>();
        data.put("bio", bio);
        data.put("bestImages", aldownloadFileUrl);
        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.userRef).document(user.getUserId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                user.setBio(bio);
                user.setBestImages(aldownloadFileUrl);
                DataHandler.getInstance().setCurrentUser(user);


                updateUserEquipments(user.getUserId());

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


    void updateUserEquipments(String userId) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> map = new HashMap<>();
        map.put("cameraAccessories", alEquipments);


        db.collection(AppConstants.cameraRef).document(userId).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                UserData user = DataHandler.getInstance().getCurrentUser();

                UserCameraResponse response = user.getUserCamera();

                response.setCameraAccessories(alEquipments);
                user.setUserCamera(response);


                DataHandler.getInstance().setCurrentUser(user);

                view.hideLoading();

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

