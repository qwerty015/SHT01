package com.autohubtraining.autohub.scene.signup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.main.MainActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignupAvatarFragment extends BaseFragment {

    @BindView(R.id.profilePic)
    CircleImageView profilePic;

    private SignupActivity activity;

    private int REQUESTCODE = 3000;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageUtils imageUtils;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_avatar, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();
        imageUtils = new ImageUtils(activity, null);

        return retView;
    }

    @OnClick({R.id.nextBtn})
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
                if (DataHandler.getInstance().getUserType() == AppConstants.CLIENT) {
                    MainActivity.startActivity(activity);
                } else {
                    activity.setViewPager(new SignupCameraInfoFragment());
                }

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
}
