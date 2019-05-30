package com.autohubtraining.autohub.scene.bestimages;


public class BestImagesPresenter implements BestImagesContract.Presenter {
    private BestImagesContract.View view;

    public BestImagesPresenter(BestImagesContract.View view) {
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
