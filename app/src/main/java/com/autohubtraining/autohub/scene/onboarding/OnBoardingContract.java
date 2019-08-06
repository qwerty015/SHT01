package com.autohubtraining.autohub.scene.onboarding;

import com.autohubtraining.autohub.scene.base.BaseView;

import org.json.JSONException;

public class OnBoardingContract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void isLogin();


        void onSignUpBtnClicked();

        void onLoginBtnClicked();
        void postData() throws JSONException;
    }

    public interface View extends BaseView {
        void navigateToSignUp();

        void navigateToHomeScreen();

        void navigateToLogin();
    }
}
