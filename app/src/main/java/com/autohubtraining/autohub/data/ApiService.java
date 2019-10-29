package com.autohubtraining.autohub.data;

import com.autohubtraining.autohub.data.model.favourite.Favourite;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Map;

public class ApiService {

    void getMyFavourite() {
        ArrayList<Favourite> alFavourite = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        UserData userData = DataHandler.getInstance().getCurrentUser();


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
}
