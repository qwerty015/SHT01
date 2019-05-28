package com.autohubtraining.autohub.onboarding;

public class OnBoardingContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onSignUpBtnClicked();
        void onLoginBtnClicked();
    }

    public interface View {
        void navigateToSignUp();
        void navigateToLogin();
    }
}
