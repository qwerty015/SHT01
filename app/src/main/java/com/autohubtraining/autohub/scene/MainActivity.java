package com.autohubtraining.autohub.scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.signup.SignupActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login, R.id.signup})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login:

                break;
            case R.id.signup:
                startActivity(new Intent(this, SignupActivity.class));
                break;
        }
    }
}
