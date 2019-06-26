package com.autohubtraining.autohub.scene.letsgo;

public class LetsGoContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
