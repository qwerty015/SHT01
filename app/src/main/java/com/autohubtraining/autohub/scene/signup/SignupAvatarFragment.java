package com.autohubtraining.autohub.scene.signup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignupAvatarFragment extends BaseFragment {

    @BindView(R.id.profilePic)
    CircleImageView profilePic;

    private SignupActivity activity;

    private String avatarPath = "";
    private int REQUESTCODE = 3000;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageUtils imageUtils;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_avatar, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();
        imageUtils = new ImageUtils(activity, null);

        return retView;
    }

    @OnClick({R.id.nextBtn, R.id.profilePic, R.id.icAdd})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.profilePic:
            case R.id.icAdd:
                if (hasPermission())
                    imageUtils.chooseFromLibrary();
                else
                    requestPermission();

                break;
            case R.id.nextBtn:
                uploadAvatarPhoto(new File(avatarPath));

                break;
        }
    }

    /**
     * method is used for get local path of image.
     *
     * @param
     * @return
     */
    public void setAvatarPath(Uri avatarUri) {
        avatarPath = ImageUtils.getFilePath(activity, avatarUri);
        Glide.with(this).load(new File(avatarPath)).into(profilePic);
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

    /**
     * method is used for uploading photo to FirebaseStorage.
     *
     * @param file
     * @return
     */
    public void uploadAvatarPhoto(File file) {
        showLoading("");

        if (file.length() > 0) {
            Uri uri = Uri.fromFile(file);

            StorageReference riversRef = storageRef.child("images/" + uri.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(uri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("profile_url", exception.getMessage());

                    showErrorToast(exception.getMessage());
                    saveDataIntoFireStore("");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            saveDataIntoFireStore(uri.toString());
                            Log.d("downloadUrl", "onSuccess: uri= " + uri.toString());
                        }
                    });
                }
            });
        } else {
            saveDataIntoFireStore("");
        }
    }

    /**
     * method is used for saving user data to FireStore.
     *
     * @param avatarUrl
     * @return
     */
    void saveDataIntoFireStore(String avatarUrl) {
        if (activity.userType == AppConstants.CLIENT) {
            User user = DataHandler.getInstance().getUser();
            user.setAvatarUrl(avatarUrl);

            /* set data into firebase database*/
            FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dismissLoading();

                    MainActivity.startActivity(activity);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissLoading();
                    showErrorToast(e.toString());
                    Log.e("firestore", "data failed with an exception" + e.toString());
                }
            });
        } else {
            dismissLoading();

            User user = DataHandler.getInstance().getUser();
            user.setAvatarUrl(avatarUrl);

            activity.setViewPager(activity.nCurrentPageIndex + 1);
        }
    }
}
