package com.autohubtraining.autohub.scene.camerainfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.camerabrand.CameraBrandActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraInfoActivity extends BaseActivity implements CameraInfoContract.View {

    private CameraInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_camera_info);
        ButterKnife.bind(this);
        setProgressBar(6);
        setup();
    }

    private void setup() {
        presenter = new CameraInfoPresenter(this);
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
        Intent intent = new Intent(this, CameraBrandActivity.class);
        startActivity(intent);
    }
}