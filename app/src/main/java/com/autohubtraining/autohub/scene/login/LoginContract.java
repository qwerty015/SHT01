package com.autohubtraining.autohub.scene.login;

import android.content.Context;

import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseView;

public class LoginContract {

    public interface Interactor {
        void checkSession();

        void doSignUp(String email, String password);

        void doSignIn(String email, String password);
    }

    public interface Presenter {
        void onCreate();

        void onDestroy();

        void checkForAuthenticatedUser();

        void validateLogin(String email, String password);

        void registerNewUser(String email, String password);
    }

    public interface Repository {
        void checkSession();

        void signUp(String email, String password);

        void signIn(String email, String password);
    }

    public interface LoginView extends BaseView {




        //void navigateToMainScreen();

        void navigateToMainScreen(UserData user);

        Context getContext();

        void navigateToSignUpScreen();

       // void navigateToNextScreen(User user);

        void showAutoRetrievedOTP(String otp);

        void showAutoRetrievingUI();

        void showAutoRetrieveingFailed();
    }

}
