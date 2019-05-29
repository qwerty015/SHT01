package com.autohubtraining.autohub.scene.otp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.password.PasswordActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OTPActivity extends AppCompatActivity implements OTPContract.View {

    private OTPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new OTPPresenter(this);
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
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }
}