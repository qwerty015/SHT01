package com.autohubtraining.autohub.signup_camera_brand;

public class SignupCameraBrandContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
