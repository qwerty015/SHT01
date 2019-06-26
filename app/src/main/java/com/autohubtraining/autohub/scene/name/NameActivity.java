package com.autohubtraining.autohub.scene.name;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.otp.OTPActivity;
import com.autohubtraining.autohub.util.Utill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN1;

public class NameActivity extends BaseActivity implements NameContract.View {

    private NamePresenter presenter;
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
        setProgressBar(SCREEN1);
        setup();
    }

    private void setup() {
        presenter = new NamePresenter(this);
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                presenter.onNextBtnClicked("","");
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this, OTPActivity.class);
        startActivity(intent);
    }

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

    @Override
    public Context getContext() {
        return this;
    }
}