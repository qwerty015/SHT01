package com.autohubtraining.autohub.scene.interest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.bestimages.BestImagesActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN7;

public class InterestActivity extends BaseActivity implements InterestContract.View {

    private InterestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        ButterKnife.bind(this);
        setProgressBar(SCREEN7);
        setup();
    }

    private void setup() {
        presenter = new InterestPresenter(this);
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
        Intent intent = new Intent(this, BestImagesActivity.class);
        startActivity(intent);
    }
}