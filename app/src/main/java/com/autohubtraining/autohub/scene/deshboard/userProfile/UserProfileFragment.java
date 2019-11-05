package com.autohubtraining.autohub.scene.deshboard.userProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseFragment;
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


public class UserProfileFragment extends BaseFragment {
    @BindView(R.id.tvName)
    CustomTextView tvUserFirstName;

    @BindView(R.id.llLogout)
    LinearLayout llLogout;


    @BindView(R.id.ivEditProfile)
    View ivEditprofile;
    @BindView(R.id.ivEditDetails)
    View ivEditDetails;

    @BindView(R.id.tvMyFavourite)
    CustomTextView tvMyFavourite;


    @BindView(R.id.ivPic)
    ImageView ivPic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View retView = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.bind(this, retView);
        setDataIntoViews();
        return retView;

    }

    @OnClick({R.id.tvMyFavourite, R.id.llLogout,R.id.ivEditDetails,R.id.ivEditProfile})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvMyFavourite:


                startActivity(new Intent(getActivity(), MyFavourites.class));
                break;

            case R.id.llLogout:
                logout();
                break;
            case R.id.ivEditProfile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.ivEditDetails:
                Intent intent1 = new Intent(getActivity(), EditDetailsActivity.class);
                startActivity(intent1);
                break;


        }
    }

    void setDataIntoViews() {


        UserData user = DataHandler.getInstance().getUserData();


        tvUserFirstName.setText(user.getFirstName() + " " + user.getLastName());


        Glide.with(getActivity()).load(user.getAvatarUrl()).transform(new CircleCrop()).placeholder(R.mipmap.profile_image_icon).into(ivPic);


    }


    @Override
    public void onStart() {
        super.onStart();
    //    EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(String str) {

        if (str != null) {
            setDataIntoViews();
            EventBus.getDefault().removeStickyEvent(str);
        }

    }


}
