package com.autohubtraining.autohub.scene.camerabrand;

import com.autohubtraining.autohub.data.model.public_data.CameraBrand;
import com.autohubtraining.autohub.data.model.public_data.CameraModel;
import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.ArrayList;

public class CameraBrandContract {

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void onNextBtnClicked(CameraBrand selectedCameraBrand, CameraModel selectedCameraModel, ArrayList<String> equipment);

        void getCameraBrands();

        void postBrand();


        void getModels(String brandId);
    }

    public interface View extends BaseView {
        void navigateToNextScreen();

        void getCameraBrands(ArrayList<CameraBrand> al);

        void getCameraModels(ArrayList<CameraModel> al);
    }

}
