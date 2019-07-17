package com.autohubtraining.autohub.scene.explore;

import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.ArrayList;
import java.util.List;

public class ExploreContract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void loadData();
    }

    public interface View extends BaseView {
        void getUsers(List<UserData> alUsers);

        void navigateToNextScreen();
    }
}
