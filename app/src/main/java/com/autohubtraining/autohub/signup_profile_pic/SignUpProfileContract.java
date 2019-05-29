package com.autohubtraining.autohub.signup_profile_pic;

public class SignUpProfileContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
