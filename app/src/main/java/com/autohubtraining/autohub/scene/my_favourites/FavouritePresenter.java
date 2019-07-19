package com.autohubtraining.autohub.scene.my_favourites;

import com.autohubtraining.autohub.data.model.favourite.Favourite;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.ArrayList;
import java.util.List;

public class FavouritePresenter {
    public interface Presenter {
        void onCreate();

        void onDestroy();

        void getMyfaourites();
    }

    public interface View extends BaseView {

        void getMyFavourites(ArrayList<Favourite> al);

    }
}
