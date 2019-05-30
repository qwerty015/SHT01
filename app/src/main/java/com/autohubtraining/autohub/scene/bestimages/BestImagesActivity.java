package com.autohubtraining.autohub.scene.bestimages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.finalscreen.LastActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BestImagesActivity extends BaseActivity implements BestImagesContract.View {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;
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


    @OnClick({R.id.nextBtn, R.id.image1, R.id.image2, R.id.image3, R.id.image4})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.onNextBtnClicked();
                break;
            case R.id.image1:
                break;
            case R.id.image2:
                break;
            case R.id.image3:
                break;
            case R.id.image4:
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, LastActivity.class);
        startActivity(intent);
    }
}