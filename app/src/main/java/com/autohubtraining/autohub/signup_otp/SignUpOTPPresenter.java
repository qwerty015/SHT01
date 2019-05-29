package com.autohubtraining.autohub.signup_otp;

import com.autohubtraining.autohub.onboarding.OnBoardingContract;
import com.autohubtraining.autohub.signup_name.SignUpNameContract;

public class SignUpOTPPresenter  implements SignUpOTPContract.Presenter {
    private SignUpOTPContract.View view;

    public SignUpOTPPresenter(SignUpOTPContract.View view) {
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
