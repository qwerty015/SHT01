package com.autohubtraining.autohub.signup_name;

import com.autohubtraining.autohub.onboarding.OnBoardingContract;

public class SignUpNamePresenter  implements SignUpNameContract.Presenter {
    private SignUpNameContract.View view;

    public SignUpNamePresenter(SignUpNameContract.View view) {
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
        if(view.isValidate()){
            view.navigateToNextScreen();
        }
    }
}
