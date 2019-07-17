package com.autohubtraining.autohub.scene.viewmore1;

public class ViewMore1Contract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void onNextBtnClicked();
    }

    public interface View {
        void navigateToNextScreen();

        void addTabLayout();
    }


}
