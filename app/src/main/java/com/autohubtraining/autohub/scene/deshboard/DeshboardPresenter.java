package com.autohubtraining.autohub.scene.deshboard;


public class DeshboardPresenter implements DeshboardContract.Presenter {
    private DeshboardContract.View view;

    public DeshboardPresenter(DeshboardContract.View view) {
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
