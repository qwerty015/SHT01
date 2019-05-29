package com.autohubtraining.autohub.signup_camera_brand;


public class SignupCameraBrandPresenter  implements SignupCameraBrandContract.Presenter {
    private SignupCameraBrandContract.View view;

    public SignupCameraBrandPresenter(SignupCameraBrandContract.View view) {
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
