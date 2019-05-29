package com.autohubtraining.autohub.scene.choose;

import com.autohubtraining.autohub.constants.AppConstants;
import com.autohubtraining.autohub.data.DataHandler;

public class ChoosePresenter implements ChooseContract.Presenter {

    private ChooseContract.View view;

    public ChoosePresenter(ChooseContract.View view){
        this.view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onClientBtnClicked() {
        DataHandler.getInstance().setUserType(AppConstants.CLIENT);
        view.navigateToSignUpName();
    }

    @Override
    public void onPhotographerBtnClicked() {
        DataHandler.getInstance().setUserType(AppConstants.PHOTOGRAPHER);
        view.navigateToSignUpName();
    }
}
