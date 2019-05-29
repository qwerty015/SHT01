package com.autohubtraining.autohub.scene.letsgo;


public class LetsGoPresenter implements LetsGoContract.Presenter {
    private LetsGoContract.View view;

    public LetsGoPresenter(LetsGoContract.View view) {
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
