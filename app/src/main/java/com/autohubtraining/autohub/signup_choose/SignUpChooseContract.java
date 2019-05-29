package com.autohubtraining.autohub.signup_choose;

public class SignUpChooseContract {

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
