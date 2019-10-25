package com.autohubtraining.autohub.scene.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomButton;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.SignupBaseActivity;
import com.autohubtraining.autohub.scene.choose.ChooseActivity;
import com.autohubtraining.autohub.scene.deshboard.DeshboardActivity;
import com.autohubtraining.autohub.scene.letsgo.LetsGoActivity;
import com.autohubtraining.autohub.scene.otp.OTPPresenter;
import com.autohubtraining.autohub.scene.profilepic.ProfileActivity;
import com.autohubtraining.autohub.util.Utill;
import com.hbb20.CountryCodePicker;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;

public class LoginActivity extends SignupBaseActivity implements LoginContract.LoginView, OnOtpCompletionListener {


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
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setup();
    }


    private void setup() {
        otpView.setOtpCompletionListener(this);
        presenter = new LoginPresenter(this);
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
                Log.e("countryCodeee", countryCodePicker.getSelectedCountryCodeWithPlus());
                if (presenter.isNumberValid(countryCodePicker)) {
                    presenter.submitPhoneNumberForVerification(countryCodePicker.getFullNumberWithPlus());
                }
                else
                {
                    Utill.showToast(getString(R.string.invalid_no), this);
                }
                break;
            case R.id.resendBtn:
                if (presenter.isNumberValid(countryCodePicker)) {


                    presenter.submitPhoneNumberForVerification(countryCodePicker.getFullNumberWithPlus());

                } else {
                    Utill.showToast(getString(R.string.invalid_no), this);
                }
                break;
        }
    }

    @Override
    public void navigateToMainScreen(UserData user) {
        DeshboardActivity.startActivity(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void navigateToSignUpScreen() {
        showErrorToast("Phone number is not regisetred");
        startActivity(new Intent(this, ChooseActivity.class));


    }

    @Override
    public void showError(String errorMessage) {
        Utill.showToast(errorMessage, this);
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void hideLoading() {
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
    public void showAutoRetrieveingFailed() {
        this.autoRetrieveTextView.setVisibility(View.GONE);
        this.resendButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onOtpCompleted(String otp) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.submitOTP(otp, "", "");
            }
        }, 200);
    }
}
