package com.autohubtraining.autohub.scene.finalscreen;

import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.ArrayList;

public class LastContract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void onNextBtnClicked(ArrayList<UserPlan> alPlans);

        void onLoginBtnClicked();
    }

    public interface View extends BaseView {
       void navigateToNextScreen();
    }


}
