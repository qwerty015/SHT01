package com.autohubtraining.autohub.scene.otp;

import com.google.firebase.auth.FirebaseUser;

public interface PhoneAuthDataListener {
    void onCodeSent(String verificationId);
    void onVerificationFailure(String errorMessage);
    void onVerificationSuccess(FirebaseUser user);
    void onOTPAutoRetrieved(String smsCode);
    void onOTPAutoRetrievalFailed(String s);
}
