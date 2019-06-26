package com.autohubtraining.autohub.scene.deshboard;

public class DeshboardContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();
    }
    
}
