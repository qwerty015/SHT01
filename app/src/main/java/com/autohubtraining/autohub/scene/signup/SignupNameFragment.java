package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.util.Utill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupNameFragment extends BaseFragment {

    @BindView(R.id.first_name)
    CustomEditView firstName;
    @BindView(R.id.last_name)
    CustomEditView lastName;

    String fNameStr, lNameStr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_name, container, false);
        ButterKnife.bind(this, retView);

        return retView;
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        SignupActivity activity = (SignupActivity) getActivity();
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                if (isValidate()) {
                    activity.setViewPager(new SignupEmailPasswordFragment());
                }
                break;
        }
    }

    private boolean isValidate() {
        fNameStr = firstName.getText().toString().trim();
        lNameStr = lastName.getText().toString().trim();

        if (TextUtils.isEmpty(fNameStr)) {
            Utill.showToast(getString(R.string.f_name_error), getActivity());
            return false;
        } else if (TextUtils.isEmpty(lNameStr)) {
            Utill.showToast(getString(R.string.l_name_error), getActivity());
            return false;
        }

        return true;
    }
}
