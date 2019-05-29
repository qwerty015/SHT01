package com.autohubtraining.autohub.scene.name;

public class NameInteractor implements NameContract.Interactor {

    private NameContract.Presenter presenter;

    public NameInteractor (NameContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void validateInput(String firstName, String lastName) {

    }
}
