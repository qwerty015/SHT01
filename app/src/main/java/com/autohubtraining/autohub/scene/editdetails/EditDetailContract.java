package com.autohubtraining.autohub.scene.editdetails;

import android.content.Context;

import com.autohubtraining.autohub.scene.base.BaseView;

import java.util.Map;

public class EditDetailContract {


    public interface Presenter {
        void onCreate();

        void onDestroy();

        void sendPhone(String phone);

        void verifyOtp(String otp, Map<String, Object> map);


    }

    public interface View extends BaseView {
        void navigateToNextScreen();

        Context getContext();

        void verifyOTP();
        void OTPVerifiedSuccessFully();
    }
}
