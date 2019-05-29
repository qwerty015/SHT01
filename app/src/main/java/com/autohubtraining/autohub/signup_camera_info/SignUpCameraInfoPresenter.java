package com.autohubtraining.autohub.signup_camera_info;


public class SignUpCameraInfoPresenter  implements SignUpCameraInfoContract.Presenter {
    private SignUpCameraInfoContract.View view;

    public SignUpCameraInfoPresenter(SignUpCameraInfoContract.View view) {
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
