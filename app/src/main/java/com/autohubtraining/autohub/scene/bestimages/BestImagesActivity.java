package com.autohubtraining.autohub.scene.bestimages;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.finalscreen.LastActivity;
import com.autohubtraining.autohub.util.ImageUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN8;

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
    File mImageFile;

    HashMap<String, String> hm = new HashMap<>();
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUESTCODE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_images);
        ButterKnife.bind(this);
        setProgressBar(SCREEN8);
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
                //navigateToNextScreen();
                presenter.onNextBtnClicked(hm);
                break;
            case R.id.image1:
            case R.id.image2:
            case R.id.image3:
            case R.id.image4:
                currentImageId = id;
                if (hasPermission())
                    imageUtils.chooseFromLibrary();
                else {
                    requestPermission();
                }
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
                imageUtils.onActivityResult(requestCode, resultCode, data, BestImagesActivity.this);
                Uri selectedImage = data.getData();
                mImageFile = new File(ImageUtils.getFilePath(this, selectedImage));
                onImageTakenFromGallery();
            }
        }
    }

    protected void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, REQUESTCODE);
    }

    boolean hasPermission() {
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;

    }

    private void onImageTakenFromGallery() {
        switch (currentImageId) {
            case R.id.image1:
                image1.setScaleType(ImageView.ScaleType.CENTER_CROP);


                Glide.with(this).load(mImageFile).into(image1);
                hm.put("image1", mImageFile.getAbsolutePath());
                //image1.setImageBitmap(bitmap);

                break;
            case R.id.image2:
                image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(mImageFile).into(image2);
                hm.put("image2", mImageFile.getAbsolutePath());

                break;
            case R.id.image3:
                image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(mImageFile).into(image3);
                hm.put("image3", mImageFile.getAbsolutePath());
                break;
            case R.id.image4:
                image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(mImageFile).into(image4);
                hm.put("image4", mImageFile.getAbsolutePath());
                break;
        }
    }

    @Override
    public void showError(String error) {
        showErrorToast(error);
    }

    @Override
    public void showLoading() {
        showLoading("Uploading Image");
    }

    @Override
    public void hideLoading() {
        dismissLoading();

    }
}