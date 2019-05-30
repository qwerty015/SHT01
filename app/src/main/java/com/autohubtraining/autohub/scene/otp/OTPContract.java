package com.autohubtraining.autohub.scene.otp;

import android.content.Context;

public class OTPContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
        void submitPhoneNumberForVerification(String phoneNumber);
        void submitOTP(String otp);
    }

    public interface View  {
        void navigateToNextScreen();
        Context getContext();
        void requestOTP();
        void showError(String errorMessage);
    }

    public interface Repository {
        void requestPhoneVerification (String phoneNumber, Context context);
        void verifyOTP(String verificationId, String otp);
    }
}
