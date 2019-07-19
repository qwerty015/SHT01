package com.autohubtraining.autohub.scene.onboarding;

import com.autohubtraining.autohub.scene.base.BaseView;

public class OnBoardingContract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void isLogin();


        void onSignUpBtnClicked();

        void onLoginBtnClicked();
    }

    public interface View extends BaseView {
        void navigateToSignUp();

        void navigateToHomeScreen();

        void navigateToLogin();
    }
}
