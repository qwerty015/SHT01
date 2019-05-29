package com.autohubtraining.autohub.signup_profile_pic;


public class SignUpProfilePresenter  implements SignUpProfileContract.Presenter {
    private SignUpProfileContract.View view;

    public SignUpProfilePresenter(SignUpProfileContract.View view) {
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
