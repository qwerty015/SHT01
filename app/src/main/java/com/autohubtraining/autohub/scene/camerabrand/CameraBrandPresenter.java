package com.autohubtraining.autohub.scene.camerabrand;


public class CameraBrandPresenter implements CameraBrandContract.Presenter {
    private CameraBrandContract.View view;

    public CameraBrandPresenter(CameraBrandContract.View view) {
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
