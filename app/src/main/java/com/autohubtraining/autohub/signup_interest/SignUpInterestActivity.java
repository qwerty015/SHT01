package com.autohubtraining.autohub.signup_interest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.signup_best_images.SignUpBestImagesActivity;
import com.autohubtraining.autohub.signup_profile_pic.SignUpProfileActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpInterestActivity extends AppCompatActivity implements SignUpInterestContract.View {

    private SignUpInterestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new SignUpInterestPresenter(this);
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
        Intent intent = new Intent(this, SignUpBestImagesActivity.class);
        startActivity(intent);
    }
}