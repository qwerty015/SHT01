package com.autohubtraining.autohub.scene.bestimages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.finalscreen.LastActivity;
import com.autohubtraining.autohub.util.ImageUtils;

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
    private ImageUtils imageUtils;
    private int currentImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_images);
        ButterKnife.bind(this);
        setProgressBar(9);
        setup();
    }

    private void setup() {
        imageUtils = new ImageUtils(this, null);
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
            case R.id.image2:
            case R.id.image3:
            case R.id.image4:
                currentImageId = id;
                imageUtils.chooseFromLibrary();
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, LastActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_LOAD_IMAGE && null != data) {
                imageUtils.onActivityResult(requestCode, resultCode, data);
                onImageTakenFromGallery();
            }
        }
    }

    private void onImageTakenFromGallery() {
        Bitmap bitmap = ImageUtils.getCompressedScaledBitmap(imageUtils.getBitmap());
        switch (currentImageId) {
            case R.id.image1:
                image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image1.setImageBitmap(bitmap);
                break;
            case R.id.image2:
                image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image2.setImageBitmap(bitmap);
                break;
            case R.id.image3:
                image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image3.setImageBitmap(bitmap);
                break;
            case R.id.image4:
                image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image4.setImageBitmap(bitmap);
                break;
        }
    }

}