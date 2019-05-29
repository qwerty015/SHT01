package com.autohubtraining.autohub.signup_otp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.onboarding.OnBoardingContract;
import com.autohubtraining.autohub.onboarding.OnBoardingPresenter;
import com.autohubtraining.autohub.signup_choose.SignUpChooseActivity;
import com.autohubtraining.autohub.signup_password.SignUpPasswordActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpOTPActivity extends AppCompatActivity implements SignUpOTPContract.View {

    private SignUpOTPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new SignUpOTPPresenter(this);
    }


    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.onNextBtnClicked();
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, SignUpPasswordActivity.class);
        startActivity(intent);
    }
}