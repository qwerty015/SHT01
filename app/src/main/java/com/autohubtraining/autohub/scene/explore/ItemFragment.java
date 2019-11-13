package com.autohubtraining.autohub.scene.explore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.events.UserFavorites;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.photographer_detail.custom.PhotoViewPagerAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autohubtraining.autohub.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemFragment extends Fragment {

    private static final String POSITON = "position";
    private static final String SCALE = "scale";
    private int screenWidth;
    private int screenHeight;

    static ItemClick itemClick;
    ImageView ivFavourite;
    UserData userData;
    int position;

    public static Fragment newInstance(FragmentActivity context, int pos, float scale, UserData userData, Fragment fragment) {
        Bundle b = new Bundle();
        b.putInt(POSITON, pos);
        b.putFloat(SCALE, scale);
        b.putSerializable("user", userData);

        itemClick = (ItemClick) fragment;


        // Log.e("workingg", "workinggg" + scale + "");
        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWidthAndHeight();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        float scale = this.getArguments().getFloat(SCALE);
        position = this.getArguments().getInt(POSITON);

        userData = (UserData) this.getArguments().getSerializable("user");


        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, screenHeight / 2);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_image, container, false);
        ViewPager vpPager = (ViewPager) linearLayout.findViewById(R.id.vpPager);
        CustomTextView tvName = linearLayout.findViewById(R.id.tvName);
        TextView tvCamera = linearLayout.findViewById(R.id.tvCamera);
        CircleImageView ivPic = linearLayout.findViewById(R.id.profile_image);


        tvName.setText(userData.getFirstName());
        if (userData.getUserCamera() != null && userData.getUserCamera().getCameraBrand() != null && userData.getUserCamera().getCameraModel() != null) {
            tvCamera.setText(userData.getUserCamera().getCameraBrand().toUpperCase() + " " + userData.getUserCamera().getCameraModel());
        }


        Glide.with(this).load(userData.getAvatarUrl()).placeholder(R.mipmap.profile_image_icon).into(ivPic);


        Button btnViewMore = linearLayout.findViewById(R.id.btnViewMore);
        ivFavourite = linearLayout.findViewById(R.id.ivFavourite);
        btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ViewMoreActivity.class);
//                intent.putExtra(AppConstants.USERDATA, userData);
//                startActivity(intent);

            }
        });
        TabLayout tabLayout = (TabLayout) linearLayout.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vpPager, true);
        CarouselLinearLayout root = (CarouselLinearLayout) linearLayout.findViewById(R.id.root_container);
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(container.getContext(), userData.getBestImages());
        vpPager.setAdapter(adapter);


        if (userData.isFavourite()) {
            ivFavourite.setImageResource(R.drawable.ic_fav_red);
        } else {
            ivFavourite.setImageResource(R.drawable.heart);
        }
        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!userData.isFavourite()) {
                    ivFavourite.setImageResource(R.drawable.ic_fav_red);


                } else {
                    ivFavourite.setImageResource(R.drawable.heart);


                }
                itemClick.setFavourite(!userData.isFavourite(), userData);
                userData.setFavourite(!userData.isFavourite());


            }
        });

        root.setScaleBoth(scale);

        return linearLayout;
    }

    /* recieving the broadcast message when user set the favourite from view more screen  */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(UserFavorites userFavorites) {

        if (userFavorites != null) {
            if (userFavorites.getUserId().equals(userData.getUserId())) {
                userData.setFavourite(Boolean.parseBoolean(userFavorites.getIsFavorite()));
                if (userData.isFavourite()) {
                    ivFavourite.setImageResource(R.drawable.ic_fav_red);
                } else {
                    ivFavourite.setImageResource(R.drawable.heart);
                }
            }

        }
        EventBus.getDefault().removeStickyEvent(userFavorites);
    }

    /**
     * Get device screen width and height
     */
    private void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }


    interface ItemClick {
        void getAction(int action);

        void setFavourite(boolean isFavourite, UserData userData);

    }

}
