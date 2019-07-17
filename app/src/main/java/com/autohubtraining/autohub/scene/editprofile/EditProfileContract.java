package com.autohubtraining.autohub.scene.editprofile;

public class EditProfileContract  {
    public interface Presenter {
        void onCreate();
        void onDestroy();

    }
    public interface View {
        void navigateToNextScreen();
    }
}
