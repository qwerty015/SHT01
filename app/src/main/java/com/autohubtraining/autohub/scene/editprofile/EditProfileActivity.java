package com.autohubtraining.autohub.scene.editprofile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.autohubtraining.autohub.customview.CustomButton;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.editinfo.EditInfoActivity;
import com.autohubtraining.autohub.scene.viewmore.ViewMoreActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.autohubtraining.autohub.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends BaseActivity {
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    MyViewPagerAdapter adapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.tvName)
    CustomTextView tvName;

    @BindView(R.id.tvCamera)
    TextView tvCamera;
    @BindView(R.id.profile_image)
    CircleImageView ivPic;


    @BindView(R.id.btnViewMore)
    CustomButton btnViewMore;


    @BindView(R.id.btnEditInfo)
    CustomButton btnEditInfo;

    int EDIT_PROFILE_INFO = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        tabLayout.setupWithViewPager(vpPager, true);

        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, EditInfoActivity.class);
                startActivity(intent);
                //startActivity(intent);


            }
        });
        btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ViewMoreActivity.class);
                intent.putExtra(AppConstants.USERDATA, DataHandler.getInstance().getCurrentUser());
                startActivity(intent);

            }
        });


        setDataIntoViews();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_INFO) {
            if (data != null && data.hasExtra("MESSAGE")) {
                String message = data.getStringExtra("MESSAGE");
                setDataIntoViews();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(String str) {
        Log.e("str", str.toString());
        if (str != null) {
            setDataIntoViews();
            EventBus.getDefault().removeStickyEvent(str);
        }

    }


    void setDataIntoViews() {


        UserData userData = DataHandler.getInstance().getCurrentUser();
        tvName.setText(userData.getFirstName().toUpperCase());


        if (userData.getUserCamera() != null && userData.getUserCamera().getCameraBrand() != null && userData.getUserCamera().getCameraModel() != null) {
            tvCamera.setText(userData.getUserCamera().getCameraBrand().toUpperCase() + " " + userData.getUserCamera().getCameraModel());
        }


        Glide.with(this).load(userData.getAvatarUrl()).placeholder(R.mipmap.profile_image_icon).into(ivPic);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, userData.getBestImages());
        vpPager.setAdapter(adapter);


    }
}
