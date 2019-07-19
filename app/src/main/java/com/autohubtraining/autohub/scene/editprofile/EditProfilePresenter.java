package com.autohubtraining.autohub.scene.editprofile;


public class EditProfilePresenter implements EditProfileContract.Presenter {
    EditProfileContract.View view;

    public EditProfilePresenter(EditProfileContract.View view) {
        view = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }
}
