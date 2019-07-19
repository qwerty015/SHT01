package com.autohubtraining.autohub.scene.my_favourites;

import android.util.Log;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.favourite.Favourite;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFavouriteContract implements FavouritePresenter.Presenter {
    FavouritePresenter.View view;

    MyFavouriteContract(FavouritePresenter.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getMyfaourites() {


        ArrayList<Favourite> alFavourite = new ArrayList<>();
        view.showLoading();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        UserData userData = DataHandler.getInstance().getCurrentUser();


        CollectionReference documentReference = db.collection(AppConstants.userRef).document(userData.getUserId()).collection(AppConstants.favourite_ref);

        documentReference.addSnapshotListener((documentSnapshot, e) -> {


            view.hideLoading();


            for (DocumentSnapshot ds : documentSnapshot.getDocuments()) {


                Map<String, Object> map = ds.getData();


                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(entry.getValue());
                    Favourite pojo = gson.fromJson(jsonElement, Favourite.class);
                    alFavourite.add(pojo);


                }

                view.getMyFavourites(alFavourite);



            }


//          HashMap<String,Object> map= (HashMap<String, Object>) documentSnapshot.getData();
//          Map<String,Object> favouriteMap= (Map<String, Object>) map.get(AppConstants.favourite_ref);
//
//
//
//          for (Map.Entry<String,Object> entry : favouriteMap.entrySet())
//
//            {
//
//                System.out.println("Key = " + entry.getKey() +
//                        ", Value = " + entry.getValue());
//
//
//                Favourite favourite= (Favourite) entry.getValue();
//
//
//
//            }


        });

    }


}
