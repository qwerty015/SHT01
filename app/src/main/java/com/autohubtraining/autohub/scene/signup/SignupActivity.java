package com.autohubtraining.autohub.scene.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.signup.custom.EquipmentAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.GlobalConstants;
import com.autohubtraining.autohub.util.ImageUtils;
import com.autohubtraining.autohub.util.ProgressBarAnimation;
import com.autohubtraining.autohub.util.views.CustomViewPager;
import com.autohubtraining.autohub.util.views.ViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity implements EquipmentAdapter.ItemClick {

    @BindView(R.id.activity_progress)
    ProgressBar progressBar;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private SignupChooseFragment fragmentChoose = new SignupChooseFragment();
    private SignupNameFragment fragmentName = new SignupNameFragment();
    private SignupEmailPasswordFragment fragmentEmailPassword = new SignupEmailPasswordFragment();
    private SignupLetsGoFragment fragmentLetsGo = new SignupLetsGoFragment();
    private SignupAvatarFragment fragmentAvatar = new SignupAvatarFragment();
    private SignupCameraInfoFragment fragmentCameraInfo = new SignupCameraInfoFragment();
    private SignupCameraBrandFragment fragmentCameraBrand = new SignupCameraBrandFragment();
    private SignupInterestFragment fragmentInterest = new SignupInterestFragment();
    private SignupBestPhotoFragment fragmentBestPhoto = new SignupBestPhotoFragment();
    private SignupPlanFragment fragmentPlan = new SignupPlanFragment();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ViewPagerAdapter viewPagerAdapter;

    String str_firstname = "";
    String str_lastname = "";
    int nCurrentPageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        viewPager.setPagingEnabled(false);

        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        viewPagerAdapter.addFragment(fragmentChoose, "title");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        if (nCurrentPageIndex == 0) {
            finish();
        } else if (nCurrentPageIndex == 1) {
            for (int i = 1; i < viewPagerAdapter.getCount(); i++) {
                viewPagerAdapter.removeFragment(i);
            }
            viewPager.setAdapter(viewPagerAdapter);
            setViewPager(0);
        } else if (nCurrentPageIndex == 3) {
            return;
        } else {
            setViewPager(nCurrentPageIndex - 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_LOAD_IMAGE && null != data) {
                if (DataHandler.getInstance().getUserType() == AppConstants.CLIENT) {
                    if (nCurrentPageIndex == 3)
                        fragmentAvatar.setAvatarPath(data.getData());
                } else {
                    if (nCurrentPageIndex == 4)
                        fragmentAvatar.setAvatarPath(data.getData());
                    else if (nCurrentPageIndex == 8)
                        fragmentBestPhoto.setImagePath(data.getData());
                }
            }
        }
    }

    @Override
    public void removeItem(int pos) { }

    public FirebaseAuth getFirebaseAuthInstance() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        return mAuth;
    }

    public FirebaseFirestore getFirebaseDB() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }

        return db;
    }

    public void setViewPager(int pageIndex) {
        nCurrentPageIndex = pageIndex;
        viewPager.setCurrentItem(pageIndex);
        setProgressBar(pageIndex);
    }

    public void initViewPager() {
        if (DataHandler.getInstance().getUserType() == AppConstants.CLIENT) {
            viewPagerAdapter.addFragment(fragmentName, "title");
            viewPagerAdapter.addFragment(fragmentEmailPassword, "title");
            viewPagerAdapter.addFragment(fragmentAvatar, "title");
        } else {
            viewPagerAdapter.addFragment(fragmentName, "title");
            viewPagerAdapter.addFragment(fragmentEmailPassword, "title");
            viewPagerAdapter.addFragment(fragmentLetsGo, "title");
            viewPagerAdapter.addFragment(fragmentAvatar, "title");
            viewPagerAdapter.addFragment(fragmentCameraInfo, "title");
            viewPagerAdapter.addFragment(fragmentCameraBrand, "title");
            viewPagerAdapter.addFragment(fragmentInterest, "title");
            viewPagerAdapter.addFragment(fragmentBestPhoto, "title");
            viewPagerAdapter.addFragment(fragmentPlan, "title");
        }

        viewPager.setAdapter(viewPagerAdapter);
        setViewPager(1);
    }

    /**
     * method is used for setting progress of the progress bar.
     *
     * @param progress
     * @return
     */
    private void setProgressBar(int progress) {
        float toValue = 0;

        progressBar.setMax(1000);
        if (DataHandler.getInstance().getUserType() == GlobalConstants.USER_CLIENT) {
            toValue = progress / (float)GlobalConstants.SIGNUP_SCREEN_COUNTS_CLIENT * 1000f;
        } else {
            toValue = progress / (float)GlobalConstants.SIGNUP_SCREEN_COUNTS_PHOTOGRAPHER * 1000f;
        }

        if (progress <= 0) {
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);
        } else{
            progressBar.setVisibility(View.VISIBLE);
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), toValue);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
        }
    }
}
