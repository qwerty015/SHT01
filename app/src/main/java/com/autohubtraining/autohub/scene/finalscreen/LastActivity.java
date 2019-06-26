package com.autohubtraining.autohub.scene.finalscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.bestimages.BestImagesActivity;
import com.autohubtraining.autohub.scene.deshboard.DeshboardActivity;
import com.autohubtraining.autohub.scene.interest.InterestPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN9;

public class LastActivity extends BaseActivity implements LastContract.View {

    private LastPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_last);
        ButterKnife.bind(this);
        setProgressBar(SCREEN9);
        setup();
    }

    private void setup() {
        presenter = new LastPresenter(this);
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
        DeshboardActivity.startActivity(this);
    }
}
