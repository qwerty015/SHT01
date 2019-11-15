package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.util.AppConstants;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupChooseFragment extends BaseFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_choose, container, false);
        ButterKnife.bind(this, retView);

        return retView;
    }

    @OnClick({R.id.client, R.id.photographer})
    void onClickItems(View view) {
        SignupActivity activity = (SignupActivity) getActivity();
        int id = view.getId();
        switch (id) {
            case R.id.client:
                activity.userType = AppConstants.CLIENT;
                activity.initViewPager();
                break;
            case R.id.photographer:
                activity.userType = AppConstants.PHOTOGRAPHER;
                activity.initViewPager();
                break;
        }
    }
}
