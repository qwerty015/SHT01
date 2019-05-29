package com.autohubtraining.autohub.scene.letsgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.profilepic.ProfileActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class LetsGoActivity extends AppCompatActivity implements LetsGoContract.View {

    private LetsGoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_lets_go);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new LetsGoPresenter(this);
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
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}