package com.autohubtraining.autohub.scene.password;

public class PasswordPresenter implements PasswordContract.Presenter {
    private PasswordContract.View view;

    public PasswordPresenter(PasswordContract.View view) {
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
