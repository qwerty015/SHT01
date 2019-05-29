package com.autohubtraining.autohub.scene;

import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.autohubtraining.autohub.R;

public class BaseActivity extends AppCompatActivity {

    protected void setProgressBar(int progress){
       ProgressBar progressBar =  findViewById(R.id.activeProgress);
       progressBar.setMax(10);
       progressBar.setProgress(progress);
    }

}
