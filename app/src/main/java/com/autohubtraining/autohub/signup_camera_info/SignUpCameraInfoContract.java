package com.autohubtraining.autohub.signup_camera_info;

public class SignUpCameraInfoContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
