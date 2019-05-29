package com.autohubtraining.autohub.scene.name;

public class NameContract {

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
