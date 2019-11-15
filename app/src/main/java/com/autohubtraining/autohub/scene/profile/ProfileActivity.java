package com.autohubtraining.autohub.scene.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.events.CustomEvent;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.layout_photographer_part)
    LinearLayout layout_photographer_part;
    @BindView(R.id.layout_client_part)
    LinearLayout layout_client_part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        User user = DataHandler.getInstance().getUser();

        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
        tv_name.setText(user.getFirstName() + " " + user.getLastName().charAt(0) + ".");

        if (user.getType() == AppConstants.CLIENT) {
            layout_client_part.setVisibility(View.VISIBLE);
            layout_photographer_part.setVisibility(View.GONE);
        } else {
            layout_client_part.setVisibility(View.GONE);
            layout_photographer_part.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_edit_profile, R.id.tv_edit_your_details, R.id.tv_manage_packages, R.id.tv_saved_profiles, R.id.layout_logout})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_edit_profile:
                if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT) {
                    Intent intent = new Intent(this, EditDetailsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, EditInfoActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tv_edit_your_details:
                if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT) {

                } else {
                    Intent intent = new Intent(this, EditDetailsActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tv_manage_packages:
                break;
            case R.id.tv_saved_profiles:
                Intent intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);

                break;
            case R.id.layout_logout:
                logout();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCustomEvent(CustomEvent event) {
        if (event.data == "Updated Profile") {
            User user = DataHandler.getInstance().getUser();

            Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
            tv_name.setText(user.getFirstName() + " " + user.getLastName().charAt(0) + ".");
        }
    }

}