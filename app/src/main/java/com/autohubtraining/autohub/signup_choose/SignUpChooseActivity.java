package com.autohubtraining.autohub.signup_choose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.signup_name.SignUpNameActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpChooseActivity extends AppCompatActivity implements SignUpChooseContract.View {

    private SignUpChooseContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_choose);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new SignUpChoosePresenter(this);
    }

    @Override
    public void navigateToSignUpName() {
        Intent intent = new Intent(this, SignUpNameActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.client, R.id.photographer})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.client:
                presenter.onClientBtnClicked();
                break;
            case R.id.photographer:
                presenter.onPhotographerBtnClicked();
                break;
        }
    }
}
