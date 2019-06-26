package com.autohubtraining.autohub.scene.interest;


public class InterestPresenter implements InterestContract.Presenter {
    private InterestContract.View view;

    public InterestPresenter(InterestContract.View view) {
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
