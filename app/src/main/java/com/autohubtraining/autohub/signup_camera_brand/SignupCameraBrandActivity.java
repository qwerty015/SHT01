package com.autohubtraining.autohub.signup_camera_brand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.signup_interest.SignUpInterestActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupCameraBrandActivity extends AppCompatActivity  implements SignupCameraBrandContract.View {

    private SignupCameraBrandPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_camera_brand);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new SignupCameraBrandPresenter(this);
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
        Intent intent = new Intent(this, SignUpInterestActivity.class);
        startActivity(intent);
    }
}