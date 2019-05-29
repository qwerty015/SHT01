package com.autohubtraining.autohub.scene.choose;

public class ChooseContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onClientBtnClicked();
        void onPhotographerBtnClicked();
    }

    public interface View {
        void navigateToSignUpName();
    }

}
