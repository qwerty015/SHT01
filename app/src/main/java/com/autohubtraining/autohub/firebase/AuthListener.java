package com.autohubtraining.autohub.firebase;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;

public interface AuthListener {
    void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential);
    void onVerificationFailed(FirebaseException e);
    void onCodeSent(String verificationId);
    void onCodeAutoRetrievalTimeOut(String s);
    void onLoginSuccess(FirebaseUser user);
    void onLoginFailed(String errorMessage);
}
