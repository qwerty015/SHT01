package com.autohubtraining.autohub.scene.camerainfo;

public class CameraInfoContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
