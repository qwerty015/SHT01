package com.autohubtraining.autohub.scene.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.editdetails.EditDetailsActivity;
import com.autohubtraining.autohub.scene.editprofile.EditProfileActivity;
import com.autohubtraining.autohub.scene.my_favourites.MyFavourites;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.ivEditProfile)
    View ivEditprofile;
    @BindView(R.id.ivEditDetails)
    View ivEditDetails;

    @BindView(R.id.tvName)
    CustomTextView tvName;

    @BindView(R.id.ivPic)
    ImageView ivPic;

    @BindView(R.id.llLogout)
    LinearLayout llLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setDataIntoViews();

        ivEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                startActivity(intent);

            }
        });
        ivEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, EditDetailsActivity.class);
                startActivity(intent);
            }
        });


        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logout();


            }
        });


    }

    @OnClick({R.id.tvMyFavourite, R.id.llLogout})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvMyFavourite:


                startActivity(new Intent(SettingsActivity.this, MyFavourites.class));
                break;

            case R.id.llLogout:
                logout();
                break;


        }
    }


    void setDataIntoViews() {
        Log.e("usreee", "settingsss");

        UserData user = DataHandler.getInstance().getCurrentUser();

        tvName.setText(user.getFirstName() + " " + user.getLastName());

        Glide.with(SettingsActivity.this).load(user.getPictureUrl()).transform(new CircleCrop()).placeholder(R.mipmap.profile_image_icon).into(ivPic);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(String str) {
        Log.e("messageEventttt", str.toString());
        if (str != null) {
            setDataIntoViews();
            EventBus.getDefault().removeStickyEvent(str);
        }

    }

}
