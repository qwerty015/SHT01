package com.autohubtraining.autohub.scene.camerainfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.SignupBaseActivity;
import com.autohubtraining.autohub.scene.camerabrand.CameraBrandActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN5;

public class CameraInfoActivity extends SignupBaseActivity implements CameraInfoContract.View {

    private CameraInfoPresenter presenter;
    @BindView(R.id.etBio)
    EditText etBio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_camera_info);
        ButterKnife.bind(this);
        setProgressBar(SCREEN5);
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
                presenter.onNextBtnClicked(etBio.getText().toString());
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, CameraBrandActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String error) {
        showErrorToast(error);
    }

    @Override
    public void showLoading() {

        showLoading("");
    }

    @Override
    public void hideLoading() {

        dismissLoading();
    }
}
