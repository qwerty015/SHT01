package com.autohubtraining.autohub.scene.bestimages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.finalscreen.LastActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BestImagesActivity extends BaseActivity implements BestImagesContract.View {

    private BestImagesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_images);
        ButterKnife.bind(this);
        setProgressBar(9);
        setup();
    }

    private void setup() {
        presenter = new BestImagesPresenter(this);
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
        Intent intent = new Intent(this, LastActivity.class);
        startActivity(intent);
    }
}