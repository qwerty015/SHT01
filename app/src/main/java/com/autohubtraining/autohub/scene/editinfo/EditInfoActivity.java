package com.autohubtraining.autohub.scene.editinfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;

import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.signup.custom.EquipmentAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.ImageUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditInfoActivity extends BaseActivity implements EditInfoContract.View, EquipmentAdapter.ItemClick {


    @BindView(R.id.etBio)
    EditText etBio;

    UserData userData;

    int currentImageId = 0;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;
    File mImageFile;


    @BindView(R.id.lvEquipment)
    ListView lvEquipment;

    @BindView(R.id.tvRemainingText)
    CustomTextView tvRemainingText;

    @BindView(R.id.ivAdd)
    ImageView ivAdd;

    EquipmentAdapter equipmentAdapter;
    ArrayList<String> alEquipments = new ArrayList<>();


    private ImageUtils imageUtils;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUESTCODE = 3000;
    HashMap<String, String> hm = new HashMap<>();
    EditInfoPresenter editInfoPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ButterKnife.bind(this);
        setup();
        setDataIntoViews();
        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setRemainingText(s.toString());


            }
        });
    }

    void setRemainingText(String s) {

        if (AppConstants.DESCRIPTIION_LENGTH - s.length() > 0) {
            tvRemainingText.setText(AppConstants.DESCRIPTIION_LENGTH - s.toString().length() + " remaining");
        } else {
            tvRemainingText.setText("");
        }

    }

    private void setup() {

        imageUtils = new ImageUtils(this, null);
        editInfoPresenter = new EditInfoPresenter(this);
        etBio.setFilters(new InputFilter[]{new InputFilter.LengthFilter(AppConstants.DESCRIPTIION_LENGTH)});


    }

    @OnClick({R.id.nextBtn, R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.ivAdd})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                editInfoPresenter.updateInfoButtonClick(hm, etBio.getText().toString(), equipmentAdapter.getEquipment());
                break;
            case R.id.ivAdd:
                alEquipments.add("");
                if (equipmentAdapter != null)
                    equipmentAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lvEquipment);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_LOAD_IMAGE && null != data) {
                imageUtils.onActivityResult(requestCode, resultCode, data, EditInfoActivity.this);
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

    void setDataIntoViews() {

        userData = DataHandler.getInstance().getUserData();
        etBio.setText(userData.getBio());
        setRemainingText(userData.getBio().toString());


        UserCameraResponse userCameraResponse = userData.getUserCamera();


        if (userCameraResponse != null && userCameraResponse.getCameraAccessories() != null) {
            alEquipments = (ArrayList<String>) userCameraResponse.getCameraAccessories();
            equipmentAdapter = new EquipmentAdapter(this, alEquipments, false);
            lvEquipment.setAdapter(equipmentAdapter);
            setListViewHeightBasedOnChildren(lvEquipment);


        } else {
            equipmentAdapter = new EquipmentAdapter(this, alEquipments, false);
            lvEquipment.setAdapter(equipmentAdapter);
            setListViewHeightBasedOnChildren(lvEquipment);
        }


        if (userData.getBestImages() != null) {


            List<String> alImages = userData.getBestImages();


            if (alImages != null) {
                if (alImages.size() == 4 || alImages.size() > 4) {
                    String image1Url = alImages.get(0);
                    String image2Url = alImages.get(1);
                    String image3Url = alImages.get(2);
                    String image4Url = alImages.get(3);
                    loadImageFromUrl(image1Url, image1);
                    loadImageFromUrl(image2Url, image2);
                    loadImageFromUrl(image3Url, image3);
                    loadImageFromUrl(image4Url, image4);


                } else if (alImages.size() == 3) {

                    String image1Url = alImages.get(0);
                    String image2Url = alImages.get(1);
                    String image3Url = alImages.get(2);


                    loadImageFromUrl(image1Url, image1);
                    loadImageFromUrl(image2Url, image2);
                    loadImageFromUrl(image3Url, image3);

                } else if (alImages.size() == 2) {
                    String image1Url = alImages.get(0);
                    String image2Url = alImages.get(1);
                    loadImageFromUrl(image1Url, image1);
                    loadImageFromUrl(image2Url, image2);


                } else if (alImages.size() == 1) {

                    String image1Url = alImages.get(0);
                    loadImageFromUrl(image1Url, image1);
                }

            }
        }
    }


    void loadImageFromUrl(String url, ImageView iv) {
        Glide.with(this).load(url).into(iv);

    }

    private void onImageTakenFromGallery() {
        //Bitmap bitmap = ImageUtils.getCompressedScaledBitmap(imageUtils.getBitmap());

        switch (currentImageId) {
            case R.id.image1:
                image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(mImageFile).into(image1);
                hm.put("image1", mImageFile.getAbsolutePath());
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);

        listView.requestLayout();
    }

    @Override
    public void navigateToNextScreen() {
        EventBus.getDefault().postSticky("Updated");
        finish();
    }

    @Override
    public void showError(String error) {
        showErrorToast(error);
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void removeItem(int pos) {

        alEquipments.remove(pos);
        equipmentAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(lvEquipment);

    }
}
