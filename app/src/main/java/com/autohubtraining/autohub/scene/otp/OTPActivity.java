package com.autohubtraining.autohub.scene.otp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomButton;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.SignupBaseActivity;
import com.autohubtraining.autohub.scene.letsgo.LetsGoActivity;
import com.autohubtraining.autohub.scene.password.PasswordActivity;
import com.autohubtraining.autohub.scene.profilepic.ProfileActivity;
import com.autohubtraining.autohub.util.Utill;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;
import static com.autohubtraining.autohub.util.AppConstants.SCREEN2;

public class OTPActivity extends SignupBaseActivity implements OTPContract.View, OnOtpCompletionListener {

    @BindView(R.id.auto_retrieve_textview)
    TextView autoRetrieveTextView;
    @BindView(R.id.countryCodePicker)
    CountryCodePicker countryCodePicker;
    @BindView(R.id.otp_view)
    OtpView otpView;
    @BindView(R.id.resendBtn)
    CustomButton resendButton;
    @BindView(R.id.mobile_no)
    CustomEditView etMobileNum;
    @BindView(R.id.nextBtn)
    CustomButton nextBtn;
    private OTPContract.Presenter presenter;
    String firstName = "", lastName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ButterKnife.bind(this);
        /* getting data from bundle*/
        FirebaseApp.initializeApp(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            firstName = bundle.getString("first_name");
            lastName = bundle.getString("last_name");


        }
        setProgressBar(SCREEN2);
        setup();
    }

    private void setup() {
        otpView.setOtpCompletionListener(this);
        presenter = new OTPPresenter(this);
        initView();
    }

    private void initView() {
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        countryCodePicker.setTypeFace(typeFace);
        countryCodePicker.setDialogTypeFace(typeFace);
        countryCodePicker.registerCarrierNumberEditText(etMobileNum);
        otpView.setTypeface(typeFace);
    }


    @OnClick({R.id.nextBtn, R.id.resendBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:

            case R.id.resendBtn:
                if (presenter.isNumberValid(countryCodePicker)) {
                    presenter.submitPhoneNumberForVerification(countryCodePicker.getFullNumberWithPlus());
                }
                else {
                    Utill.showToast(getString(R.string.invalid_no), this);
                }
                break;
        }
    }

    @Override
    public void navigateToNextScreen(User user) {
        Intent intent = null;
        if (DataHandler.getInstance().getUserType() == PHOTOGRAPHER)
            intent = new Intent(this, PasswordActivity.class);
        else
            intent = new Intent(this, ProfileActivity.class);

        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String errorMessage) {
        Utill.showToast(errorMessage, this);
    }

    @Override
    public void showLoader() {
        showLoading("");
    }

    @Override
    public void hideLoader() {
        dismissLoading();
    }

    @Override
    public void showAutoRetrievingUI() {
        this.resendButton.setVisibility(View.GONE);
        this.autoRetrieveTextView.setVisibility(View.VISIBLE);
        this.otpView.setVisibility(View.VISIBLE);
        this.nextBtn.setEnabled(false);
    }

    @Override
    public void showAutoRetrievedOTP(String otp) {
        this.autoRetrieveTextView.setVisibility(View.GONE);
        this.otpView.setText(otp);
    }

    @Override
    public void onOtpCompleted(final String otp) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.submitOTP(otp, firstName, lastName);
            }
        }, 200);
    }

    @Override
    public void showAutoRetrieveingFailed() {
        this.autoRetrieveTextView.setVisibility(View.GONE);
        this.resendButton.setVisibility(View.VISIBLE);
    }
}