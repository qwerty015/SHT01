package com.autohubtraining.autohub.scene.interest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.bestimages.BestImagesActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN7;

public class InterestActivity extends BaseActivity implements InterestContract.View {

    private InterestPresenter presenter;
    @BindView(R.id.photoShootCB)
    CheckBox photoShootCB;

    @BindView(R.id.videographyCB)
    CheckBox videographyCB;

    ArrayList<String> alUserInterest = new ArrayList<>();


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
                alUserInterest.clear();
                if (photoShootCB.isChecked()) {
                    alUserInterest.add("PHOTO SHOOT");
                }
                if (videographyCB.isChecked()) {
                    alUserInterest.add("VIDEOGRAPHY");
                }
                presenter.onNextBtnClicked(alUserInterest);
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, BestImagesActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String error) {


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