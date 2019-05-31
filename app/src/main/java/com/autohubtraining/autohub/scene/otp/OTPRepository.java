package com.autohubtraining.autohub.scene.otp;

import android.content.Context;

import com.autohubtraining.autohub.firebase.AuthListener;
import com.autohubtraining.autohub.firebase.PhoneAuthUtil;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPRepository implements OTPContract.Repository, AuthListener {

    private PhoneAuthDataListener dataListener;
    private PhoneAuthUtil phoneAuth;

    OTPRepository(PhoneAuthDataListener dataListener) {
        this.dataListener = dataListener;
        this.phoneAuth = new PhoneAuthUtil(this);
    }

    @Override
    public void requestPhoneVerification(String phoneNumber, Context context) {
        phoneAuth.verifyPhoneNumber(phoneNumber, context);
    }

    @Override
    public void verifyOTP(String vId, String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vId, otp);
        phoneAuth.signIn(credential);
    }

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        dataListener.onOTPAutoRetrieved(phoneAuthCredential.getSmsCode());
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
        dataListener.onVerificationFailure(e.getMessage());
    }

    @Override
    public void onCodeSent(String verificationId) {
        dataListener.onCodeSent(verificationId);
    }

    @Override
    public void onCodeAutoRetrievalTimeOut(String s) {
        dataListener.onOTPAutoRetrievalFailed(s);
    }

    @Override
    public void onLoginSuccess(FirebaseUser user) {
        dataListener.onVerificationSuccess();
    }

    @Override
    public void onLoginFailed(String errorMessage) {
        dataListener.onVerificationFailure(errorMessage);
    }
}
