package com.autohubtraining.autohub.signup_letsgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.onboarding.OnBoardingContract;
import com.autohubtraining.autohub.onboarding.OnBoardingPresenter;
import com.autohubtraining.autohub.signup_choose.SignUpChooseActivity;
import com.autohubtraining.autohub.signup_profile_pic.SignUpProfileActivity;
import com.autohubtraining.autohub.util.Utill;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpLetsGoActivity extends AppCompatActivity implements SignUpLetsGoContract.View {

    private SignUpLetsGoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_lets_go);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new SignUpLetsGoPresenter(this);
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
        Intent intent = new Intent(this, SignUpProfileActivity.class);
        startActivity(intent);
    }
}