package com.autohubtraining.autohub.scene.profilepic;

import com.autohubtraining.autohub.scene.base.BaseView;

import java.io.File;

public class ProfileContract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void onNextBtnClicked();

        void uploadPic(File file);


    }

    public interface View extends BaseView {
        void navigateToNextScreen();

        void showLoading();

        void hideLoading();


    }

}
