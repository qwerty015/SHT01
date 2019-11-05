package com.autohubtraining.autohub.scene.explore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.favourite.Favourite;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExplorePresenter implements ExploreContract.Presenter {
    private ExploreContract.View view;
    List<UserData> alUsers = new ArrayList<>();
    int index = 0;
    ArrayList<Favourite> alFavourite = new ArrayList<>();
    boolean isUserDataLoaded = false;


    public ExplorePresenter(ExploreContract.View view) {
        this.view = view;
        getMyFavourite();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }


    public void setFavourite(UserData user, boolean isFavourite) {
        //view.showLoading();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        UserData currentUserData = DataHandler.getInstance().getUserData();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> likeMap = new HashMap<>();

        likeMap.put("name", user.getFirstName());
        likeMap.put("id", user.getUserId());
        likeMap.put("is_favourite", isFavourite);
        map.put(user.getUserId(), likeMap);


        if (isFavourite) {
            db.collection(AppConstants.ref_user).document(currentUserData.getUserId()).collection(AppConstants.favourite_ref).document(user.getUserId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // view.hideLoading();
                    view.navigateToNextScreen();


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
            db.collection(AppConstants.ref_user).document(currentUserData.getUserId()).collection(AppConstants.favourite_ref).document(user.getUserId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // view.hideLoading();
                    view.navigateToNextScreen();


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


    @Override
    public void loadData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        CollectionReference documentReference = db.collection(AppConstants.ref_user);
        Query query = documentReference.whereEqualTo("type", AppConstants.PHOTOGRAPHER);


        alUsers.clear();

        query.addSnapshotListener((documentSnapshot, e) -> {

            Log.e("workingg", isUserDataLoaded + "");
            if (isUserDataLoaded) {
                return;
            }
            if (documentSnapshot.getDocuments() != null) {
                index = documentSnapshot.getDocuments().size();


                UserData currentUser = DataHandler.getInstance().getUserData();
                List<DocumentSnapshot> alDocuments = documentSnapshot.getDocuments();

                for (DocumentSnapshot snapshot : alDocuments) {


                    UserData user = snapshot.toObject(UserData.class);


                    if (!user.getUserId().equals(currentUser.getUserId())) {
                        getCameraBrands(user);
                    }


                }
            }


        });


    }

    void getMyFavourite() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        UserData userData = DataHandler.getInstance().getUserData();

        CollectionReference documentReference = db.collection(AppConstants.ref_user).document(userData.getUserId()).collection(AppConstants.favourite_ref);
        documentReference.addSnapshotListener((documentSnapshot, e) -> {

            for (DocumentSnapshot ds : documentSnapshot.getDocuments()) {
                Map<String, Object> map = ds.getData();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(entry.getValue());
                    Favourite pojo = gson.fromJson(jsonElement, Favourite.class);
                    alFavourite.add(pojo);
                }


            }
        });

    }


    void filterData(String filter) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        CollectionReference documentReference = db.collection(AppConstants.ref_user);

        Query query = documentReference.whereEqualTo("type", AppConstants.PHOTOGRAPHER).whereGreaterThan("firstName", filter);
        // query=query.whereArrayContains("firstName",filter);
        query.addSnapshotListener((documentSnapshot, e) -> {


            Log.e("filter", documentSnapshot.getDocuments().toString());
            index = documentSnapshot.getDocuments().size();


            alUsers.clear();
            List<DocumentSnapshot> alDocuments = documentSnapshot.getDocuments();

            for (DocumentSnapshot snapshot : alDocuments) {
                UserData user = snapshot.toObject(UserData.class);


                getCameraBrands(user);


            }


        });

    }


    void getCameraBrands(UserData user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection(AppConstants.ref_camera).document(user.getUserId()).addSnapshotListener((documentSnapshot, e) -> {


            //Map<String, Object> map = documentSnapshot.getData();
            UserCameraResponse userCameraResponse = documentSnapshot.toObject(UserCameraResponse.class);
            user.setUserCamera(userCameraResponse);

            getServicePlans(user);


        });

    }


    void getServicePlans(UserData user) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.ref_service_plan).document(user.getUserId()).addSnapshotListener((documentSnapshot, e) -> {

            ArrayList<UserPlan> alBrands = new ArrayList<>();
            Map<String, Object> map = documentSnapshot.getData();

            if (map != null) {

                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                //please check


                while (itr.hasNext()) {
                    HashMap<String, Object> brand = (HashMap<String, Object>) itr.next().getValue();

                    //UserPlan userPlan = (UserPlan) itr.next().getValue();


                    UserPlan userPlan = new UserPlan();
                    if (brand.get("planName") != null)
                        userPlan.setPlanName(brand.get("planName").toString());
                    if (brand.get("numberOfPictures") != null)
                        userPlan.setNumberOfPictures(brand.get("numberOfPictures").toString());
                    if (brand.get("shootType") != null)
                        userPlan.setShootType(brand.get("shootType").toString());
                    if (brand.get("price") != null)
                        userPlan.setPrice(brand.get("amount").toString());
                    if (brand.get("editingIncluded") != null)
                        userPlan.setEditingIncluded(brand.get("editingIncluded").toString());
                    alBrands.add(userPlan);
                    Log.e("usererere", userPlan.getPlanName());


                }
            }


            user.setFavourite(false);
            for (Favourite favourite : alFavourite) {

                if (favourite.getId().equals(user.getUserId())) {
                    user.setFavourite(true);
                    break;
                }

            }
            user.setAlUserPlans(alBrands);
            alUsers.add(user);


            if ((index - 1) == alUsers.size())
                view.getUsers(alUsers);

            isUserDataLoaded = true;


        });
    }


}
