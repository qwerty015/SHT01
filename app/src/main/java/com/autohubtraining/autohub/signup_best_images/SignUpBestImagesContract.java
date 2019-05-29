package com.autohubtraining.autohub.signup_best_images;

public class SignUpBestImagesContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
