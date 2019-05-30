package com.autohubtraining.autohub.scene.otp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.password.PasswordActivity;
import com.autohubtraining.autohub.util.Utill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OTPActivity extends BaseActivity implements OTPContract.View {

    private OTPPresenter presenter;

    @BindView(R.id.otp)
    CustomEditView editView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ButterKnife.bind(this);
        setProgressBar(2);
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
                presenter.submitPhoneNumberForVerification("+91 9098358687");
                break;
            case R.id.resendBtn:
                presenter.submitOTP("123456");
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, PasswordActivity.class);
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
}