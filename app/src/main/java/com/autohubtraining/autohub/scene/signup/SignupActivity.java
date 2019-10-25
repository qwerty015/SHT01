package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.GlobalConstants;
import com.autohubtraining.autohub.util.ProgressBarAnimation;
import com.autohubtraining.autohub.util.views.CustomViewPager;
import com.autohubtraining.autohub.util.views.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.activity_progress)
    ProgressBar progressBar;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        setViewPager(new SignupChooseFragment());
    }

    @Override
    public void onBackPressed() {
        if (viewPagerAdapter.getCount() <= 1) {
            finish();
        } else {
            viewPagerAdapter.removeFragment(viewPagerAdapter.getCount() - 1);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(viewPagerAdapter.getCount() - 1);
        }

        setProgressBar(viewPagerAdapter.getCount() - 1);
    }

    public void setViewPager(Fragment fragment) {
        viewPagerAdapter.addFragment(fragment, "title");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(viewPagerAdapter.getCount() - 1);

        setProgressBar(viewPagerAdapter.getCount() - 1);
    }

    private void setProgressBar(int progress) {
        float toValue = 0;

        progressBar.setMax(1000);
        if (DataHandler.getInstance().getUserType() == GlobalConstants.USER_CLIENT) {
            toValue = progress / (float)GlobalConstants.SIGNUP_SCREEN_COUNTS_CLIENT * 1000f;
        } else {
            toValue = progress / (float)GlobalConstants.SIGNUP_SCREEN_COUNTS_PHOTOGRAPHER * 1000f;
        }

        if (progress <= 0) {
            progressBar.setProgress(0);
        } else{
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), toValue);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
        }
    }
}
