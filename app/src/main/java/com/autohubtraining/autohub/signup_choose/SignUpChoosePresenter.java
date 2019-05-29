package com.autohubtraining.autohub.signup_choose;

import com.autohubtraining.autohub.constants.AppConstants;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.onboarding.OnBoardingContract;

public class SignUpChoosePresenter implements SignUpChooseContract.Presenter {

    private SignUpChooseContract.View view;

    public SignUpChoosePresenter(SignUpChooseContract.View view){
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onClientBtnClicked() {
        DataHandler.getInstance().setUserType(AppConstants.CLIENT);
        view.navigateToSignUpName();
    }

    @Override
    public void onPhotographerBtnClicked() {
        DataHandler.getInstance().setUserType(AppConstants.PHOTOGRAPHER);
        view.navigateToSignUpName();
    }
}
