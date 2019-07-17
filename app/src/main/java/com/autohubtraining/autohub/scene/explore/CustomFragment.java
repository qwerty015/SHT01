package com.autohubtraining.autohub.scene.explore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.editprofile.MyViewPagerAdapter;
import com.autohubtraining.autohub.scene.viewmore.ViewMoreActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.views.CustomLinearLayout;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomFragment extends Fragment {

    public static Fragment newInstance(Context context, int position, float scale, UserData userDAta) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putFloat("scale", scale);
        bundle.putSerializable("data",userDAta);
        return Fragment.instantiate(context, CustomFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout linearLayout = (LinearLayout)
                inflater.inflate(R.layout.fragment_image, container, false);

        UserData userData = (UserData) this.getArguments().getSerializable("data");




        ViewPager vpPager = (ViewPager) linearLayout.findViewById(R.id.vpPager);
        CustomTextView tvName = linearLayout.findViewById(R.id.tvName);
        TextView tvCamera = linearLayout.findViewById(R.id.tvCamera);
        CircleImageView ivPic = linearLayout.findViewById(R.id.profile_image);


        tvName.setText(userData.getFirstName());
        if (userData.getUserCamera() != null && userData.getUserCamera().getCameraBrand() != null && userData.getUserCamera().getCameraModel() != null) {
            tvCamera.setText(userData.getUserCamera().getCameraBrand().toUpperCase() + " " + userData.getUserCamera().getCameraModel());
        }


        Glide.with(getContext()).load(userData.getPictureUrl()).placeholder(R.mipmap.profile_image_icon).into(ivPic);

        Button btnViewMore = linearLayout.findViewById(R.id.btnViewMore);

        btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewMoreActivity.class);
                intent.putExtra(AppConstants.USERDATA, userData);
                getContext().startActivity(intent);

            }
        });
        TabLayout tabLayout = (TabLayout) linearLayout.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vpPager, true);

        CustomLinearLayout root = (CustomLinearLayout) linearLayout.findViewById(R.id.root_container);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getContext(), userData.getBestImages());
        vpPager.setAdapter(adapter);



        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return linearLayout;
    }
}
