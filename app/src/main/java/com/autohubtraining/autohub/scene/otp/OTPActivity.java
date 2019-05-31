package com.autohubtraining.autohub.scene.otp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomButton;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.letsgo.LetsGoActivity;
import com.autohubtraining.autohub.scene.profilepic.ProfileActivity;
import com.autohubtraining.autohub.util.Utill;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;
import static com.autohubtraining.autohub.util.AppConstants.SCREEN2;

public class OTPActivity extends BaseActivity implements OTPContract.View, OnOtpCompletionListener{

    private OTPContract.Presenter presenter;

    @BindView(R.id.auto_retrieve_textview)
    TextView autoRetrieveTextView;

    @BindView(R.id.otp_view)
    OtpView otpView;

    @BindView(R.id.resendBtn)
    CustomButton resendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ButterKnife.bind(this);
        setProgressBar(SCREEN2);
        setup();
    }

    private void setup() {
        otpView.setOtpCompletionListener(this);
        presenter = new OTPPresenter(this);
    }


    @OnClick({R.id.nextBtn, R.id.resendBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.submitPhoneNumberForVerification("+917000762503");
                break;
            case R.id.resendBtn:
                presenter.submitPhoneNumberForVerification("+917000762503");
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = null;
        if (DataHandler.getInstance().getUserType() == PHOTOGRAPHER)
            intent = new Intent(this, LetsGoActivity.class);
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
    public void showAutoRetrievingUI () {
        this.resendButton.setVisibility(View.GONE);
        this.autoRetrieveTextView.setVisibility(View.VISIBLE);
        this.otpView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAutoRetrievedOTP (String otp) {
        this.autoRetrieveTextView.setVisibility(View.GONE);
        this.otpView.setText(otp);
    }

    @Override
    public void onOtpCompleted(final String otp) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.submitOTP(otp);
            }
        }, 200);
    }

    @Override
    public void showAutoRetrieveingFailed() {
        this.autoRetrieveTextView.setVisibility(View.GONE);
        this.resendButton.setVisibility(View.VISIBLE);
    }
}