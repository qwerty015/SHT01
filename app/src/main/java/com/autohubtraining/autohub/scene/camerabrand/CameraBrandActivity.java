package com.autohubtraining.autohub.scene.camerabrand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.interest.InterestActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN6;
import static com.autohubtraining.autohub.util.AppConstants.SCREEN7;

public class CameraBrandActivity extends BaseActivity implements CameraBrandContract.View {

    private CameraBrandPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_camera_brand);
        ButterKnife.bind(this);
        setProgressBar(SCREEN6);
        setup();
    }

    private void setup() {
        presenter = new CameraBrandPresenter(this);
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
        Intent intent = new Intent(this, InterestActivity.class);
        startActivity(intent);
    }
}