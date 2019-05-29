package com.autohubtraining.autohub.scene.finalscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;

public class LastActivity extends BaseActivity implements LastContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_last);
        setProgressBar(10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
