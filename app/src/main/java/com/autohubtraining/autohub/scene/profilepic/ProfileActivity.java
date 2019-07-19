package com.autohubtraining.autohub.scene.profilepic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.camerainfo.CameraInfoActivity;
import com.autohubtraining.autohub.scene.deshboard.DeshboardActivity;
import com.autohubtraining.autohub.util.ImageUtils;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.autohubtraining.autohub.util.AppConstants.CLIENT;
import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;
import static com.autohubtraining.autohub.util.AppConstants.SCREEN3;
import static com.autohubtraining.autohub.util.AppConstants.SCREEN4;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {
    @BindView(R.id.profilePic)
    CircleImageView profilePic;
    private ProfilePresenter presenter;
    private ImageUtils imageUtils;
    String path = "";
    int REQUESTCODE = 3000;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_image);
        ButterKnife.bind(this);

        if (DataHandler.getInstance().getUserType() == PHOTOGRAPHER)
            setProgressBar(SCREEN4);
        else
            setProgressBar(SCREEN3);
        setup();
    }

    private void setup() {
        imageUtils = new ImageUtils(this, null);
        presenter = new ProfilePresenter(this);
    }


    @OnClick({R.id.nextBtn, R.id.profilePic, R.id.icAdd})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.uploadPic(new File(path));
                break;
            case R.id.profilePic:

            case R.id.icAdd:
                if (hasPermission())
                    imageUtils.chooseFromLibrary();
                else
                    requestPermission();
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        if (DataHandler.getInstance().getUserType() == CLIENT) {
            DeshboardActivity.startActivity(this);

        } else {
            Intent intent = new Intent(this, CameraInfoActivity.class);
            startActivity(intent);
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

    @Override
    public void showLoading() {

        showLoading("Uploading Image");

    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_LOAD_IMAGE && null != data) {
                //imageUtils.onActivityResult(requestCode, resultCode, data);
                //Bitmap bitmap = onImageTakenFromGallery();

                Uri selectedImage = data.getData();
                path = ImageUtils.getFilePath(this, selectedImage);
                //Log.e("filePath",path);
                Glide.with(this).load(new File(path)).into(profilePic);


            }
        }
    }

    private Bitmap onImageTakenFromGallery() {
        Bitmap bitmap = ImageUtils.getCompressedScaledBitmap(imageUtils.getBitmap());
        profilePic.setImageBitmap(bitmap);
        return bitmap;
    }

    @Override
    public void showError(String error) {

    }
}