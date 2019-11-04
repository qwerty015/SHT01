package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupPlanFragment extends BaseFragment {

    SignupActivity activity = (SignupActivity) getActivity();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_plan, container, false);
        ButterKnife.bind(this, retView);

        return retView;
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                MainActivity.startActivity(activity);
                break;
        }
    }
}
