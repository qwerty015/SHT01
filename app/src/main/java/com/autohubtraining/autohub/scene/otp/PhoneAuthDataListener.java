package com.autohubtraining.autohub.scene.otp;

import com.google.firebase.auth.PhoneAuthCredential;

public interface PhoneAuthDataListener {
    void onCodeSent(String verificationId);
    void onVerificationFailure(String errorMessage);
    void onVerificationSuccess();
}
