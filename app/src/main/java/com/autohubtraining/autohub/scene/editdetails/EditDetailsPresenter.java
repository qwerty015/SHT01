package com.autohubtraining.autohub.scene.editdetails;

import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.firebase.AuthListener;
import com.autohubtraining.autohub.firebase.PhoneAuthUtil;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class EditDetailsPresenter implements EditDetailContract.Presenter, AuthListener {

    private PhoneAuthProvider authProvider;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private AuthListener firebaseListener;
    private PhoneAuthUtil phoneAuth;
    EditDetailContract.View view;
    String verificationId = "";
    Map<String, Object> map = new HashMap<>();


    EditDetailsPresenter(EditDetailContract.View view) {
        phoneAuth = new PhoneAuthUtil(this);
        this.view = view;

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void sendPhone(String phone) {
        view.showLoading();
        phoneAuth.verifyPhoneNumber(phone, this.view.getContext());


    }

    @Override
    public void verifyOtp(String otp, Map<String, Object> map) {

        view.showLoading();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e("datatataaaa", task.toString());


                if (task.isSuccessful()) {

                    view.OTPVerifiedSuccessFully();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                view.showError(e.getMessage());
                view.hideLoading();

                Log.e("errorr", e.toString());
            }
        });


//
//        firebaseUser.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // Log.d(TAG, "User re-authenticated.");
//                        if (task.isSuccessful()) {
//
//                            firebaseUser.updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//
//
//                                    Log.e("datatataaaa", task.toString());
//
//                                }
//                            });
//                            //updateUserEmail();
//                        } else {
//                            // Password is incorrect
//                        }
//                    }
//                });


    }

    void updateData(Map<String, Object> map) {
        view.showLoading();
        UserData userData = DataHandler.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(userData.getUserId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                if (map.get("phoneNo") != null)
                    userData.setPhoneNo(map.get("phoneNo").toString());
                userData.setFirstName(map.get("firstName").toString());
                userData.setCountryCode(map.get("countryCode").toString());
                userData.setLastName(map.get("lastName").toString());
                DataHandler.getInstance().setCurrentUser(userData);

                view.hideLoading();
                view.navigateToNextScreen();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                view.hideLoading();
                view.showError(e.toString());
            }
        });

    }


    public boolean isNumberValid(CountryCodePicker countryCodePicker) {

        return countryCodePicker.isValidFullNumber();
    }

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


    }

    @Override
    public void onVerificationFailed(FirebaseException e) {

        view.hideLoading();
        view.showError(e.getMessage());
    }

    @Override
    public void onCodeSent(String verificationId) {


        view.hideLoading();
        this.verificationId = verificationId;


        view.verifyOTP();


    }

    @Override
    public void onCodeAutoRetrievalTimeOut(String s) {
        view.showError(s.toString());
        view.hideLoading();
    }

    @Override
    public void onLoginSuccess(FirebaseUser user) {

    }

    @Override
    public void onLoginFailed(String errorMessage) {

    }
}
