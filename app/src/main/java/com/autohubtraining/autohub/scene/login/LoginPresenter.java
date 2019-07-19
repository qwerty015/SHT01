package com.autohubtraining.autohub.scene.login;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;

import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.otp.OTPContract;
import com.autohubtraining.autohub.scene.otp.OTPRepository;
import com.autohubtraining.autohub.scene.otp.PhoneAuthDataListener;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

public class LoginPresenter implements OTPContract.Presenter, PhoneAuthDataListener {
    private LoginContract.LoginView view;
    private OTPContract.Repository repository;
    private String verificationId;
    User user = new User();
    FirebaseFirestore db;

    public LoginPresenter(LoginContract.LoginView view) {
        this.view = view;
        this.repository = new OTPRepository(this);
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNextBtnClicked() {

    }


    @Override
    public void onCodeSent(String verificationId) {
        view.hideLoading();
        this.verificationId = verificationId;
        this.view.showAutoRetrievingUI();
    }

    @Override
    public void onVerificationFailure(String errorMessage) {
        view.hideLoading();
        this.view.showError(errorMessage);
    }

    @Override
    public void onVerificationSuccess(FirebaseUser user) {
        view.hideLoading();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection(AppConstants.userRef).document(user.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    view.hideLoading();
                    DocumentSnapshot doc = task.getResult();
                    Log.e("workingggggg", "signup");

                    if (doc.getData() != null) {

                        UserData user = doc.toObject(UserData.class);
                        DataHandler.getInstance().setCurrentUser(user);
                        view.navigateToMainScreen(user);
                    } else {
                        view.navigateToSignUpScreen();

                    }


                }


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.hideLoading();
                        view.showError(e.toString());
                        //view.navigateToSignUpScreen();
                    }
                });


    }


    //view.navigateToNextScreen(this.user);


    @Override
    public void onOTPAutoRetrieved(String smsCode) {
        view.showAutoRetrievedOTP(smsCode);


    }

    @Override
    public void onOTPAutoRetrievalFailed(String s) {
        view.showAutoRetrieveingFailed();
    }

    //actions
//    @Override
//    public void onNextBtnClicked() {
//        view.navigateToSignUpScreen(this.user);
//    }

    @Override
    public void submitPhoneNumberForVerification(String phoneNumber) {
        view.showLoading();
        this.repository.requestPhoneVerification(phoneNumber, this.view.getContext());
    }

    @Override
    public void submitOTP(String otp, String firstName, String lastName) {
        view.showLoading();

        this.repository.verifyOTP(verificationId, otp);
    }

    @Override
    public boolean isNumberValid(CountryCodePicker countryCodePicker) {

        return countryCodePicker.isValidFullNumber();
    }
}
