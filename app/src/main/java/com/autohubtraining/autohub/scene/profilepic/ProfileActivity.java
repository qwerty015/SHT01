package com.autohubtraining.autohub.scene.profilepic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.camerainfo.CameraInfoActivity;
import com.autohubtraining.autohub.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {
    @BindView(R.id.profilePic)
    CircleImageView profilePic;
    private ProfilePresenter presenter;
    private ImageUtils imageUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_image);
        ButterKnife.bind(this);
        setProgressBar(5);
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
                presenter.onNextBtnClicked();
                break;
            case R.id.profilePic:
            case R.id.icAdd:
                imageUtils.chooseFromLibrary();
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, CameraInfoActivity.class);
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
        profilePic.setImageBitmap(bitmap);
    }
}