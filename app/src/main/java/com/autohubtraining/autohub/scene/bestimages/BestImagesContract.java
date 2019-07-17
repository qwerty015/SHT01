package com.autohubtraining.autohub.scene.bestimages;

import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.HashMap;

public class BestImagesContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked(HashMap<String, String> hm);
    }

    public interface View extends BaseView {
        void navigateToNextScreen();
    }
    
}
