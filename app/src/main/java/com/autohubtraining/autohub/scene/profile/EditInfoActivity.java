package com.autohubtraining.autohub.scene.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.signup.custom.EquipmentAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditInfoActivity extends BaseActivity implements EquipmentAdapter.ItemClick {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.et_bio)
    EditText et_bio;
    @BindView(R.id.tv_remainingbio)
    TextView tv_remainingbio;
    @BindView(R.id.tv_camerainfo)
    TextView tv_camerainfo;
    @BindView(R.id.lv_equipment)
    ListView lv_equipment;

    EquipmentAdapter equipmentAdapter;
    ArrayList<String> alEquipments = new ArrayList<>();

    int currentImageId = 0;
    private ImageUtils imageUtils;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUESTCODE = 3000;
    HashMap<String, String> hm = new HashMap<>();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> aldownloadFileUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ButterKnife.bind(this);

        imageUtils = new ImageUtils(this, null);
        et_bio.setFilters(new InputFilter[]{new InputFilter.LengthFilter(AppConstants.DESCRIPTIION_LENGTH)});

        et_bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                setRemainingBioText(s.toString());
            }
        });

        showUserDetails();
    }

    @OnClick({R.id.b_savechanges, R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.iv_addequipment, R.id.tv_editcamera})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.b_savechanges:
                saveButtonClick();

                break;
            case R.id.iv_addequipment:
                alEquipments.add("");

                if (equipmentAdapter != null)
                    equipmentAdapter.notifyDataSetChanged();

                setListViewHeightBasedOnChildren(lv_equipment);

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
            case R.id.tv_editcamera:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_LOAD_IMAGE && null != data) {
                imageUtils.onActivityResult(requestCode, resultCode, data, this);
                setImagePath(data.getData());
            }
        }
    }

    @Override
    public void removeItem(int pos) {
        alEquipments.remove(pos);
        equipmentAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(lv_equipment);
    }

    /**
     * method is used for displaying users's details to view.
     *
     * @param
     * @return
     */
    private void showUserDetails() {
        User user = DataHandler.getInstance().getUser();

        if (user.getBestImages() != null) {
            List<String> list_images = user.getBestImages();

            if (list_images.size() >= 4) {
                Glide.with(this).load(list_images.get(0)).into(image1);
                Glide.with(this).load(list_images.get(1)).into(image2);
                Glide.with(this).load(list_images.get(2)).into(image3);
                Glide.with(this).load(list_images.get(3)).into(image4);
            } else if (list_images.size() == 3) {
                Glide.with(this).load(list_images.get(0)).into(image1);
                Glide.with(this).load(list_images.get(1)).into(image2);
                Glide.with(this).load(list_images.get(2)).into(image3);
            } else if (list_images.size() == 2) {
                Glide.with(this).load(list_images.get(0)).into(image1);
                Glide.with(this).load(list_images.get(1)).into(image2);
            } else if (list_images.size() == 1) {
                Glide.with(this).load(list_images.get(0)).into(image1);
                Glide.with(this).load(list_images.get(1)).into(image2);
            }
        }

        et_bio.setText(user.getBio());
        setRemainingBioText(user.getBio().toString());

        tv_camerainfo.setText(user.getCameraBrand() + " " + user.getCameraModel());

        if (user.getCameraAccessories() != null) {
            alEquipments = (ArrayList<String>) user.getCameraAccessories();
            equipmentAdapter = new EquipmentAdapter(this, alEquipments, false);
            lv_equipment.setAdapter(equipmentAdapter);
            setListViewHeightBasedOnChildren(lv_equipment);
        } else {
            equipmentAdapter = new EquipmentAdapter(this, alEquipments, false);
            lv_equipment.setAdapter(equipmentAdapter);
            setListViewHeightBasedOnChildren(lv_equipment);
        }
    }

    /**
     * method is used for requesting permission to read from external storage.
     *
     * @param
     * @return
     */
    protected void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, REQUESTCODE);
    }

    /**
     * method is used for checking permission to read from external storage.
     *
     * @param
     * @return boolean true or false for permission or not
     */
    boolean hasPermission() {
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * method is used for checking remaining characters of bio and show/hide the indicator text_view.
     *
     * @param bio
     * @return
     */
    void setRemainingBioText(String bio) {
        if (AppConstants.DESCRIPTIION_LENGTH - bio.length() > 0) {
            tv_remainingbio.setText(AppConstants.DESCRIPTIION_LENGTH - bio.toString().length() + " remaining");
        } else {
            tv_remainingbio.setText("");
        }
    }

    /**
     * method is used for loading image to image_view and setting local path of image.
     *
     * @param
     * @return
     */
    private void setImagePath(Uri imageUri) {
        File mImageFile = new File(ImageUtils.getFilePath(this, imageUri));

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

    private void saveButtonClick() {
        showLoading("");

        String image1 = hm.get("image1");
        if (image1 != null && image1.length() > 0)
            al.add(image1);

        String image2 = hm.get("image2");
        if (image2 != null && image2.length() > 0)
            al.add(image2);

        String image3 = hm.get("image3");
        if (image3 != null && image3.length() > 0)
            al.add(image3);

        String image4 = hm.get("image4");
        if (image4 != null && image4.length() > 0)
            al.add(image4);

        for (int i = 0; i < al.size(); i++) {
            uploadImage(new File(al.get(i)));
        }

        if (al.size() == 0) {
            saveDataIntoFireStore();
        }
    }

    /**
     * method is used for uploading image to FirebaseStorage.
     *
     * @param file
     * @return
     */
    void uploadImage(File file) {
        Uri uri = Uri.fromFile(file);

        StorageReference imgRef = storageRef.child("images/" + uri.getLastPathSegment());
        UploadTask uploadTask = imgRef.putFile(uri);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(AppConstants.TAG, exception.getMessage());

                dismissLoading();
                showErrorToast(exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e(AppConstants.TAG, "image uploaded");

                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        aldownloadFileUrl.add(uri.toString());

                        if (aldownloadFileUrl.size() == al.size()) {
                            saveDataIntoFireStore();
                        }
                    }
                });
            }
        });
    }

    /**
     * method is used for saving user data to Firestore.
     *
     * @param
     * @return
     */
    void saveDataIntoFireStore() {
        User user = DataHandler.getInstance().getUser();

        if (user.getBestImages() != null) {
            for (String str : user.getBestImages()) {
                aldownloadFileUrl.add(str);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("bio", et_bio.getText().toString());
        data.put("bestImages", aldownloadFileUrl);
        data.put("cameraAccessories", alEquipments);

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                user.setBio(et_bio.getText().toString());
                user.setBestImages(aldownloadFileUrl);
                user.setCameraAccessories(alEquipments);

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();

                showErrorToast(e.getMessage());
                Log.e(AppConstants.TAG, "data failed with an exception" + e.toString());
            }
        });
    }
}
