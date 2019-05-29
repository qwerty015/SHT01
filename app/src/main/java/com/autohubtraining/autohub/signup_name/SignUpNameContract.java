package com.autohubtraining.autohub.signup_name;

public class SignUpNameContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
        boolean isValidate();
    }

}
