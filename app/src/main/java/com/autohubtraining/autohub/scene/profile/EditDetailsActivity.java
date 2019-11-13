package com.autohubtraining.autohub.scene.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.events.CustomEvent;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
import com.autohubtraining.autohub.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.et_firstname)
    EditText et_firstname;
    @BindView(R.id.et_lastname)
    EditText et_lastname;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_visiblepassword)
    TextView tv_visiblepassword;

    private String s_firstname = "";
    private String s_lastname = "";
    private String avatarPath = "";
    private int REQUESTCODE = 3000;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageUtils imageUtils;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_details);
        ButterKnife.bind(this);

        imageUtils = new ImageUtils(this, null);

        showUserDetails();
    }

    @OnClick({R.id.iv_avatar, R.id.tv_visiblepassword, R.id.b_savechanges})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_avatar:
                if (hasPermission())
                    imageUtils.chooseFromLibrary();
                else
                    requestPermission();

                break;
            case R.id.tv_visiblepassword:
                break;
            case R.id.b_savechanges:
                if (isNameValidate()) {
                    uploadAvatarPhoto(new File(avatarPath));
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_LOAD_IMAGE && null != data) {
                setAvatarPath(data.getData());
            }
        }
    }

    /**
     * method is used for displaying users's details to view.
     *
     * @param
     * @return
     */
    private void showUserDetails() {
        User user = DataHandler.getInstance().getUser();

        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
        et_firstname.setText(user.getFirstName());
        et_lastname.setText(user.getLastName());
        et_email.setText(user.getEmail());

        et_email.setEnabled(false);
        et_password.setEnabled(false);
    }

    /**
     * method is used for get local path of image.
     *
     * @param
     * @return
     */
    private void setAvatarPath(Uri avatarUri) {
        avatarPath = ImageUtils.getFilePath(this, avatarUri);
        Glide.with(this).load(new File(avatarPath)).into(iv_avatar);
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
     * method is used for checking validation of first name and last name.
     *
     * @param
     * @return boolean true for valid or false or invalid
     */
    private boolean isNameValidate() {
        s_firstname = et_firstname.getText().toString().trim();
        s_lastname = et_lastname.getText().toString().trim();

        if (TextUtils.isEmpty(s_firstname)) {
            AppUtils.showToast(getString(R.string.f_name_error), this);
            return false;
        } else if (TextUtils.isEmpty(s_lastname)) {
            AppUtils.showToast(getString(R.string.l_name_error), this);
            return false;
        }

        return true;
    }

    /**
     * method is used for uploading photo to FirebaseStorage.
     *
     * @param file
     * @return
     */
    private void uploadAvatarPhoto(File file) {
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
                    saveUserDataIntoFireStore("");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            saveUserDataIntoFireStore(uri.toString());
                            Log.d("downloadUrl", "onSuccess: uri= " + uri.toString());
                        }
                    });
                }
            });
        } else {
            saveUserDataIntoFireStore("");
        }
    }

    /**
     * method is used for saving user data to Firestore.
     *
     * @param avatarUrl avatar link of storage
     * @return
     */
    private void saveUserDataIntoFireStore(String avatarUrl) {
        User user = DataHandler.getInstance().getUser();

        Map<String, Object> data = new HashMap<>();
        data.put("firstName", s_firstname);
        data.put("lastName", s_lastname);

        if (!avatarUrl.isEmpty()) {
            data.put("avatarUrl", avatarUrl);
        }

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                user.setFirstName(s_firstname);
                user.setLastName(s_lastname);

                if (!avatarUrl.isEmpty()) {
                    user.setAvatarUrl(avatarUrl);
                }

                EventBus.getDefault().postSticky(new CustomEvent("Updated Profile"));
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
