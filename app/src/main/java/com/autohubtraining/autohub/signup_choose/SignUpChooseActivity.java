package com.autohubtraining.autohub.signup_choose;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.autohubtraining.autohub.R;

public class SignUpChooseActivity extends AppCompatActivity implements SignUpChooseContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_choose);
    }

    @Override
    public void handleSignIn() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void navigateToMainScreen() {

    }

}
