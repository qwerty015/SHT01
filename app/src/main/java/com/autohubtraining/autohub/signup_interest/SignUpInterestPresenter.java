package com.autohubtraining.autohub.signup_interest;


public class SignUpInterestPresenter  implements SignUpInterestContract.Presenter {
    private SignUpInterestContract.View view;

    public SignUpInterestPresenter(SignUpInterestContract.View view) {
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
