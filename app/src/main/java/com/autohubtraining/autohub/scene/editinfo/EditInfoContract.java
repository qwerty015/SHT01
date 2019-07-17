package com.autohubtraining.autohub.scene.editinfo;

import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.ArrayList;
import java.util.HashMap;

public class EditInfoContract {
    public interface Presenter {
        void onCreate();
        void onDestroy();
        void updateInfoButtonClick(HashMap<String,String> hm,String bio, ArrayList<String> equipment);

    }
    public interface View  extends BaseView {
        void navigateToNextScreen();
    }
}
