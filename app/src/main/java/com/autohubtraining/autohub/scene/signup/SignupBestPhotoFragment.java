package com.autohubtraining.autohub.scene.signup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.main.MainActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupBestPhotoFragment extends BaseFragment {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    SignupActivity activity;
    private ImageUtils imageUtils;
    private int currentImageId;

    HashMap<String, String> hm = new HashMap<>();
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUESTCODE = 3000;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> aldownloadFileUrl = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_best_photo, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();
        imageUtils = new ImageUtils(activity, null);

        return retView;
    }

    @OnClick({R.id.nextBtn, R.id.image1, R.id.image2, R.id.image3, R.id.image4})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                onNextBtnClicked();

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

    /**
     * method is used for set local path of image.
     *
     * @param
     * @return
     */
    public void setImagePath(Uri imageUri) {
        File mImageFile = new File(ImageUtils.getFilePath(activity, imageUri));

        switch (currentImageId) {
            case R.id.image1:
                image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(activity).load(mImageFile).into(image1);
                hm.put("image1", mImageFile.getAbsolutePath());

                break;
            case R.id.image2:
                image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(activity).load(mImageFile).into(image2);
                hm.put("image2", mImageFile.getAbsolutePath());

                break;
            case R.id.image3:
                image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(activity).load(mImageFile).into(image3);
                hm.put("image3", mImageFile.getAbsolutePath());
                break;
            case R.id.image4:
                image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(activity).load(mImageFile).into(image4);
                hm.put("image4", mImageFile.getAbsolutePath());
                break;
        }
    }

    /**
     * method is used for requesting permission to read from external storage.
     *
     * @param
     * @return
     */
    protected void requestPermission() {
        ActivityCompat.requestPermissions(activity, permissions, REQUESTCODE);
    }

    /**
     * method is used for checking permission to read from external storage.
     *
     * @param
     * @return boolean true or false for permission or not
     */
    boolean hasPermission() {
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(activity, str) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    void onNextBtnClicked() {
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
                        Log.e(AppConstants.TAG, aldownloadFileUrl.size() + " " + al.size());

                        if (aldownloadFileUrl.size() == al.size()) {
                            saveDataIntoFireStore();
                        }

                        Log.d("downloadUrl", "onSuccess: uri= " + uri.toString());
                    }
                });
            }
        });
    }

    /**
     * method is used for saving user data to FireStore.
     *
     * @param
     * @return
     */
    void saveDataIntoFireStore() {
        dismissLoading();

        User user = DataHandler.getInstance().getUser();
        user.setBestImages(aldownloadFileUrl);

        UserData userData = DataHandler.getInstance().getUserData();
        userData.setBestImages(aldownloadFileUrl);

        activity.setViewPager(activity.nCurrentPageIndex + 1);
    }
}
