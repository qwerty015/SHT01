package com.autohubtraining.autohub.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.autohubtraining.autohub.scene.otp.DataListener;
import com.autohubtraining.autohub.scene.otp.PhoneAuthDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthUtil {

    private PhoneAuthProvider authProvider;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private AuthListener firebaseListener;

    public PhoneAuthUtil(AuthListener firebaseListener) {
        authProvider = PhoneAuthProvider.getInstance();
        initFirebaseCallbacks();
        this.firebaseListener = firebaseListener;
    }

    private void initFirebaseCallbacks() {
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                firebaseListener.onVerificationCompleted(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                firebaseListener.onVerificationFailed(e);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                firebaseListener.onCodeSent(s);
            }
        };
    }

    public void verifyPhoneNumber (String phoneNumber, Context context) {
        authProvider.verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, (Activity) context, mCallback);
    }

    public void signIn (PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = task.getResult().getUser();
                    firebaseListener.onLoginSuccess(user);
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        firebaseListener.onLoginFailed(task.getException().getMessage());
                    }
                }
            }
        });
    }

}
