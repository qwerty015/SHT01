package com.autohubtraining.autohub.scene.homescreen;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.settings.SettingsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    @BindView(R.id.ivPic)
    ImageView ivPic;

    @BindView(R.id.btnFindPhotographer)
    Button btnFindPhotographer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.activity_home_screen, container, false);
        ButterKnife.bind(this, retView);
        setDataIntoViews();
        retView.findViewById(R.id.ivEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);


            }
        });

        btnFindPhotographer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ViewPager vp = getActivity().findViewById(R.id.viewpager1);
                vp.setCurrentItem(1);
            }
        });
        return retView;
    }

    void setDataIntoViews() {


        UserData user = DataHandler.getInstance().getUserData();

        if (user != null && user.getAvatarUrl() != null)
            Glide.with(getActivity()).load(user.getAvatarUrl()).transform(new CircleCrop()).placeholder(R.mipmap.profile_image_icon).into(ivPic);


    }




}
