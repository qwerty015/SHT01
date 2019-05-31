package com.autohubtraining.autohub.scene.finalscreen;


import com.autohubtraining.autohub.scene.interest.InterestContract;

public class LastPresenter implements LastContract.Presenter {

    private LastContract.View view;

    public LastPresenter(LastContract.View view) {
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

    @Override
    public void onLoginBtnClicked() {

    }

}
