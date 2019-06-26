package com.autohubtraining.autohub.scene.onboarding;

public class OnBoardingPresenter implements OnBoardingContract.Presenter {
    private OnBoardingContract.View view;

    public OnBoardingPresenter(OnBoardingContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSignUpBtnClicked() {
        view.navigateToSignUp();
    }

    @Override
    public void onLoginBtnClicked() {
        view.navigateToLogin();
    }
}
