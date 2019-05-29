package com.autohubtraining.autohub.scene.camerainfo;


public class CameraInfoPresenter implements CameraInfoContract.Presenter {
    private CameraInfoContract.View view;

    public CameraInfoPresenter(CameraInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked() {
        view.navigateToNextScreen();
    }

}
