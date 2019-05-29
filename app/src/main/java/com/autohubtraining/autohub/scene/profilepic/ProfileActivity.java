package com.autohubtraining.autohub.scene.profilepic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.camerainfo.CameraInfoActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    private ProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_image);
        ButterKnife.bind(this);
        setProgressBar(5);
        setup();
    }

    private void setup() {
        presenter = new ProfilePresenter(this);
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
        Intent intent = new Intent(this, CameraInfoActivity.class);
        startActivity(intent);    }
}