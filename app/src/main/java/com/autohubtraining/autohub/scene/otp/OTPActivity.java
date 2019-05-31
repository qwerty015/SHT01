package com.autohubtraining.autohub.scene.otp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.letsgo.LetsGoActivity;
import com.autohubtraining.autohub.scene.profilepic.ProfileActivity;
import com.autohubtraining.autohub.util.Utill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;
import static com.autohubtraining.autohub.util.AppConstants.SCREEN2;

public class OTPActivity extends BaseActivity implements OTPContract.View {

    @BindView(R.id.otp)
    CustomEditView editView;
    private OTPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ButterKnife.bind(this);
        setProgressBar(SCREEN2);
        setup();
    }

    private void setup() {
        presenter = new OTPPresenter(this);
    }


    @OnClick({R.id.nextBtn, R.id.resendBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                navigateToNextScreen();
                // presenter.submitPhoneNumberForVerification("+91 9098358687");
                break;
            case R.id.resendBtn:
                presenter.submitOTP("123456");
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
    public void requestOTP() {
        //do anything UI related like show OTP input fields or disable/enable widgets.
        Utill.showToast("Please provide OTP.", this);
        editView.setEnabled(true);
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
}