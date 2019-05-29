package com.autohubtraining.autohub.scene.name;

public class NamePresenter implements NameContract.Presenter {

    private NameContract.View view;
    private NameContract.Interactor interactor;

    public NamePresenter(NameContract.View view) {
        this.view = view;
        interactor = new NameInteractor(this);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void onNextBtnClicked(String firstName, String secondName) {
        view.navigateToNextScreen();
    }

}