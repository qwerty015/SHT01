package com.autohubtraining.autohub.scene.camerainfo;

import com.autohubtraining.autohub.scene.base.BaseView;

public class CameraInfoContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked(String bio);
    }

    public interface View extends BaseView {
        void navigateToNextScreen();

    }
    
}
