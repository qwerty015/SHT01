package com.autohubtraining.autohub.scene.viewmore1;

public class ViewMore1Presenter implements ViewMore1Contract.Presenter {

    private ViewMore1Contract.View view;

    public ViewMore1Presenter(ViewMore1Activity viewMore1Activity) {
        this.view = viewMore1Activity;
    }


    @Override
    public void onCreate() {
        view.addTabLayout();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked() {

        view.navigateToNextScreen();
    }
}
