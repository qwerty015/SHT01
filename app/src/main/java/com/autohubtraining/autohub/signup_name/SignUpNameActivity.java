package com.autohubtraining.autohub.signup_name;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.onboarding.OnBoardingContract;
import com.autohubtraining.autohub.onboarding.OnBoardingPresenter;
import com.autohubtraining.autohub.signup_choose.SignUpChooseActivity;
import com.autohubtraining.autohub.signup_otp.SignUpOTPActivity;
import com.autohubtraining.autohub.signup_otp.SignUpOTPContract;
import com.autohubtraining.autohub.util.Utill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpNameActivity extends AppCompatActivity implements SignUpNameContract.View {

    private SignUpNamePresenter presenter;
    @BindView(R.id.first_name)
    CustomEditView firstName;
    @BindView(R.id.last_name)
    CustomEditView lastName;
    String fNameStr,lNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_name);
        ButterKnife.bind(this);
        setup();
    }

    private void setup() {
        presenter = new SignUpNamePresenter(this);
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.onNextBtnClicked();
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, SignUpOTPActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean isValidate() {
        fNameStr = firstName.getText().toString().trim();
        lNameStr = lastName.getText().toString().trim();
        if(TextUtils.isEmpty(fNameStr)){
            Utill.showToast(getString(R.string.f_name_error),this);
            return false;
        }else if(TextUtils.isEmpty(lNameStr)){
            Utill.showToast(getString(R.string.l_name_error),this);
            return false;
        }
        return true;
    }
}