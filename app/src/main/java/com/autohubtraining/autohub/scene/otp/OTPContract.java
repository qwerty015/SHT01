package com.autohubtraining.autohub.scene.otp;

import android.content.Context;

import com.autohubtraining.autohub.data.model.User;
import com.hbb20.CountryCodePicker;

public class OTPContract {

    public interface Presenter {
        void onCreate();
        void onDestroy();
        void onNextBtnClicked();
        void submitPhoneNumberForVerification(String phoneNumber);
        void submitOTP(String otp,String firstName,String lastName);
        boolean isNumberValid(CountryCodePicker countryCodePicker);
    }

    public interface View  {
        void navigateToNextScreen(User user);
        Context getContext();
        void showError(String errorMessage);
        void showLoader();
        void hideLoader();
        void showAutoRetrievedOTP(String otp);
        void showAutoRetrievingUI();
        void showAutoRetrieveingFailed();
    }

    public interface Repository {
        void requestPhoneVerification (String phoneNumber, Context context);
        void verifyOTP(String verificationId, String otp);



    }
}
