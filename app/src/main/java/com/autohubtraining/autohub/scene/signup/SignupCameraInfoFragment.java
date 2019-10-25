package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupCameraInfoFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_camera_info, container, false);
        ButterKnife.bind(this, retView);

        return retView;
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        SignupActivity activity = (SignupActivity) getActivity();
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                activity.setViewPager(new SignupCameraBrandFragment());
                break;
        }
    }
}
