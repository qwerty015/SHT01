package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class SignupEmailPasswordFragment extends BaseFragment {

    @BindView(R.id.email)
    CustomEditView email;
    @BindView(R.id.password)
    CustomEditView password;
    @BindView(R.id.confirm_password)
    CustomEditView confirm_password;

    String emailStr, passwordStr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_email_password, container, false);
        ButterKnife.bind(this, retView);

        return retView;
    }

    @OnClick({R.id.nextBtn, R.id.visiblePwdBtn})
    void onClickItems(View view) {
        SignupActivity activity = (SignupActivity) getActivity();
        int id = view.getId();
        switch (id) {
            case R.id.visiblePwdBtn:
                if (password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.nextBtn:
                if (isValidate()) {
                    activity.setViewPager(new SignupLetsGoFragment());
                }
                break;
        }
    }

    private boolean isValidate() {
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailStr)) {
            Utill.showToast(getString(R.string.email_empty_error), getActivity());
            return false;
        } else if (TextUtils.isEmpty(passwordStr)) {
            Utill.showToast(getString(R.string.password_error), getActivity());
            return false;
        }

        return true;
    }
}
