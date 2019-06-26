package com.autohubtraining.autohub.scene;

import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.util.Loading;

import butterknife.BindView;

import static com.autohubtraining.autohub.util.AppConstants.MAX_SCREEN_CLIENT;
import static com.autohubtraining.autohub.util.AppConstants.MAX_SCREEN_PHOTOGRAPHER;
import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;

public class BaseActivity extends AppCompatActivity {
    private Loading loading;
    @BindView(R.id.activeProgress)
    ProgressBar progressBar;

    protected void setProgressBar(int progress){
        if(DataHandler.getInstance().getUserType() == PHOTOGRAPHER){
            progressBar.setMax(MAX_SCREEN_PHOTOGRAPHER);
        } else{
            progressBar.setMax(MAX_SCREEN_CLIENT);
        }
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
