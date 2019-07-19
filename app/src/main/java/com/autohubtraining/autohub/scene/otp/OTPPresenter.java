package com.autohubtraining.autohub.scene.otp;


import android.util.Log;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class OTPPresenter implements OTPContract.Presenter, PhoneAuthDataListener {
    private OTPContract.View view;
    private OTPContract.Repository repository;
    private String verificationId;
    User user = new User();
    FirebaseFirestore db;
    String countryCode = "";


    public OTPPresenter(OTPContract.View view) {
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
    public void onCodeSent(String verificationId) {
        view.hideLoader();
        this.verificationId = verificationId;
        this.view.showAutoRetrievingUI();
    }

    @Override
    public void onVerificationFailure(String errorMessage) {
        view.hideLoader();
        this.view.showError(errorMessage);
    }

    @Override
    public void onVerificationSuccess(FirebaseUser firebaseUser) {
        view.hideLoader();


        //Log.e("userree",  user.getPhoneNumber().substring(this.user.getCountryCode().length()));


        this.user.setPhoneNo(firebaseUser.getPhoneNumber().substring(this.user.getCountryCode().length()));



        /* saving first name and last name into FIREBASE DATABASE*/
        this.user.setUserId(firebaseUser.getUid());


        this.user.setType(DataHandler.getInstance().getUserType());


        db.collection(AppConstants.userRef).document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

               if (documentSnapshot.exists() && !documentSnapshot.getData().isEmpty()) {

                    view.showError("Users Already Registred. Please Login ");

                } else {



                    db.collection(AppConstants.userRef).document(firebaseUser.getUid()).set(user);


                    DataHandler.getInstance().setUser(user);

                    view.navigateToNextScreen(user);
                }

            }
        });


    }


    @Override
    public void onOTPAutoRetrieved(String smsCode) {
        view.showAutoRetrievedOTP(smsCode);


    }

    @Override
    public void onOTPAutoRetrievalFailed(String s) {
        view.showAutoRetrieveingFailed();
    }

    //actions
    @Override
    public void onNextBtnClicked() {
        view.navigateToNextScreen(this.user);
    }

    @Override
    public void submitPhoneNumberForVerification(String phoneNumber) {
        view.showLoader();

        this.repository.requestPhoneVerification(phoneNumber, this.view.getContext());
    }

    @Override
    public void submitOTP(String otp, String firstName, String lastName) {
        view.showLoader();
        this.user.setFirstName(firstName);
        this.user.setLastName(lastName);
        this.repository.verifyOTP(verificationId, otp);
    }

    @Override
    public boolean isNumberValid(CountryCodePicker countryCodePicker) {


        this.user.setCountryCode(countryCodePicker.getSelectedCountryCodeWithPlus());


        return countryCodePicker.isValidFullNumber();
    }
}
