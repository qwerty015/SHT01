package com.autohubtraining.autohub.signup_password;

import com.autohubtraining.autohub.onboarding.OnBoardingContract;
import com.autohubtraining.autohub.signup_name.SignUpNameContract;

public class SignUpPasswordPresenter  implements SignUpPasswordContract.Presenter {
    private SignUpPasswordContract.View view;

    public SignUpPasswordPresenter(SignUpPasswordContract.View view) {
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
