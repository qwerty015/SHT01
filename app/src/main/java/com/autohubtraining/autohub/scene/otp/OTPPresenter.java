package com.autohubtraining.autohub.scene.otp;

public class OTPPresenter implements OTPContract.Presenter {
    private OTPContract.View view;

    public OTPPresenter(OTPContract.View view) {
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
