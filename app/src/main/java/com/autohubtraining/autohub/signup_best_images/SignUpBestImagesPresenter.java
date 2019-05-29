package com.autohubtraining.autohub.signup_best_images;


public class SignUpBestImagesPresenter  implements SignUpBestImagesContract.Presenter {
    private SignUpBestImagesContract.View view;

    public SignUpBestImagesPresenter(SignUpBestImagesContract.View view) {
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
