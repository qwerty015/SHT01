package com.autohubtraining.autohub.signup_letsgo;

public class SignUpLetsGoContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
