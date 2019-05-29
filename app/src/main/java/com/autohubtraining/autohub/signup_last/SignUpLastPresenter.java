package com.autohubtraining.autohub.signup_last;


import com.autohubtraining.autohub.signup_interest.SignUpInterestContract;

public class SignUpLastPresenter implements SignUpInterestContract.Presenter {

    private SignUpInterestContract.View view;

    public SignUpLastPresenter(SignUpInterestContract.View view) {
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
