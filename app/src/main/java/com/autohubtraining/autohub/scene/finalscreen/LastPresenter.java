package com.autohubtraining.autohub.scene.finalscreen;


import com.autohubtraining.autohub.scene.interest.InterestContract;

public class LastPresenter implements InterestContract.Presenter {

    private InterestContract.View view;

    public LastPresenter(InterestContract.View view) {
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
