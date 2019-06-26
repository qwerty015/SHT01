package com.autohubtraining.autohub.scene.profilepic;

public class ProfileContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
