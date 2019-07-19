package com.autohubtraining.autohub.scene.interest;

import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.ArrayList;

public class InterestContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked(ArrayList<String> alUserInterest);
    }

    public interface View extends BaseView {
        void navigateToNextScreen();
    }
    
}
