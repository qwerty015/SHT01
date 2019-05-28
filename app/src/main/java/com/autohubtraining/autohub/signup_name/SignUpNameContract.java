package com.autohubtraining.autohub.signup_name;

public class SignUpNameContract {

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

    public interface View {
        void handleSignIn();
        void showError();
        void navigateToMainScreen();
    }
    
}
