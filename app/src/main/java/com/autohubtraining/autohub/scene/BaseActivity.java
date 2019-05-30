package com.autohubtraining.autohub.scene;

import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.util.Loading;

import static com.autohubtraining.autohub.util.AppConstants.MAX_SCREEN_PHOTOGRAPHER;

public class BaseActivity extends AppCompatActivity {

    private Loading loading;

    protected void setProgressBar(int progress){
       ProgressBar progressBar =  findViewById(R.id.activeProgress);
       progressBar.setMax(MAX_SCREEN_PHOTOGRAPHER);
       progressBar.setProgress(progress);
    }

    protected void showLoading(String message) {
        if (loading == null) {
            loading = new Loading(this);
        }
        if (!isFinishing()) {
            loading.show(message);
        }
    }

    protected void dismissLoading() {
        if (loading != null && loading.isShowing() && !this.isFinishing()) {
            loading.dismiss();
        }
    }

}
