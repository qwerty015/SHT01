package com.autohubtraining.autohub.scene.camerabrand;

public class CameraBrandContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
