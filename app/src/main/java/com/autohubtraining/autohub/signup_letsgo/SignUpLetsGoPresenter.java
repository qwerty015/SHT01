package com.autohubtraining.autohub.signup_letsgo;


public class SignUpLetsGoPresenter  implements SignUpLetsGoContract.Presenter {
    private SignUpLetsGoContract.View view;

    public SignUpLetsGoPresenter(SignUpLetsGoContract.View view) {
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
