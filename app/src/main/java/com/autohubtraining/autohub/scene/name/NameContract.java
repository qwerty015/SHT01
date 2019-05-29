package com.autohubtraining.autohub.scene.name;

import android.content.Context;

public class NameContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked(String firstName, String lastName);
    }

    public interface View {
        void navigateToNextScreen();
        Context getContext();
    }

    public interface Interactor {
        void validateInput(String firstName, String lastName);
    }
}
