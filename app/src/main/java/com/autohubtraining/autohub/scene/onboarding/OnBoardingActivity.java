package com.autohubtraining.autohub.scene.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.choose.ChooseActivity;
import com.autohubtraining.autohub.scene.name.NameActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingActivity extends AppCompatActivity implements OnBoardingContract.View {

    private OnBoardingContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        setup();
    }

    private void setup(){
        presenter = new OnBoardingPresenter(this);
    }

    @Override
    public void navigateToSignUp() {
        startActivity(new Intent(this, ChooseActivity.class));
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, NameActivity.class));
    }

    @OnClick({R.id.login, R.id.signup})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login:
                presenter.onLoginBtnClicked();
                break;
            case R.id.signup:
                presenter.onSignUpBtnClicked();
                break;
        }
    }
}
