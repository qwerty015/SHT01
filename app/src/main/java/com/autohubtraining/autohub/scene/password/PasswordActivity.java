package com.autohubtraining.autohub.scene.password;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.letsgo.LetsGoActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;


public class PasswordActivity extends BaseActivity implements PasswordContract.View {

    private PasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_password);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new PasswordPresenter(this);
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
        Intent intent = new Intent(this, LetsGoActivity.class);
        startActivity(intent);
    }
}