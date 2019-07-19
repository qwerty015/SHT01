package com.autohubtraining.autohub.scene.viewmore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.events.UserFavorites;
import com.autohubtraining.autohub.data.model.public_data.CameraBrand;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;
import com.autohubtraining.autohub.scene.explore.ExplorePresenter;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.editprofile.MyViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewMoreActivity extends AppCompatActivity implements ViewMoreContract.ViewMoreView {
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    MyViewPagerAdapter adapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tabs)
    TabLayout tabLayout1;
    @BindView(R.id.tvName)
    CustomTextView tvName;

    @BindView(R.id.btnViewMore)
    CustomTextView btnViewMore;


    @BindView(R.id.tvCamera)
    TextView tvCamera;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.tvEquipments)
    TextView tvEquipments;
    @BindView(R.id.tvEditingIncluded)
    CustomTextView tvEditingIncluded;

    @BindView(R.id.tvNoOfPhotographs)
    CustomTextView tvNoOfPhotographs;

    @BindView(R.id.ivFavourite)
    ImageView ivFavourite;


    ViewMorePresenter presenter;

    UserData userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);

        ButterKnife.bind(this);


        if (getIntent().getExtras().containsKey(AppConstants.USERDATA)) {


            userData = (UserData) getIntent().getExtras().getSerializable(AppConstants.USERDATA);

            setDataIntoViews(userData);
        }


        presenter = new ViewMorePresenter(this);
        presenter.onCreate();

        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!userData.isFavourite()) {
                    ivFavourite.setImageResource(R.drawable.ic_fav_red);


                } else {
                    ivFavourite.setImageResource(R.drawable.heart);


                }
                presenter.setFavourite(userData, !userData.isFavourite());
                userData.setFavourite(!userData.isFavourite());


            }
        });

    }


    void setDataIntoViews(UserData userData) {

        adapter = new MyViewPagerAdapter(ViewMoreActivity.this, userData.getBestImages());
        vpPager.setAdapter(adapter);


        tvName.setText(userData.getFirstName()+" "+userData.getLastName());
        if (userData.getUserCamera() != null && userData.getUserCamera().getCameraBrand() != null && userData.getUserCamera().getCameraModel() != null) {
            tvCamera.setText(userData.getUserCamera().getCameraBrand().toUpperCase() + " " + userData.getUserCamera().getCameraModel());
        }


        Glide.with(this).load(userData.getPictureUrl()).placeholder(R.mipmap.profile_image_icon).into(profile_image);
        btnViewMore.setText(userData.getBio());


        UserCameraResponse cameraResponse = userData.getUserCamera();

        String equipmewnts = "";

        if (cameraResponse != null) {


            ArrayList<String> alEquipments = (ArrayList<String>) cameraResponse.getCameraAccessories();

            if (alEquipments != null) {

                for (String str : alEquipments) {


                    equipmewnts = str + " \n" + equipmewnts;


                }

            }


        }


        tvEquipments.setText(equipmewnts);

        if (userData.isFavourite()) {
            ivFavourite.setImageResource(R.drawable.ic_fav_red);
        } else {
            ivFavourite.setImageResource(R.drawable.heart);
        }


    }

    @Override
    public void addTabLayout() {
        tabLayout.setupWithViewPager(vpPager, true);


        if (userData != null && userData.getAlUserPlans() != null) {


            for (int i = 0; i < userData.getAlUserPlans().size(); i++) {

                if (i < 2) {
                    UserPlan plan = userData.getAlUserPlans().get(i);

                    tabLayout1.addTab(tabLayout1.newTab().setText("RS. " + plan.getAmount().toString()));
                }

            }

        } else {


            tabLayout1.addTab(tabLayout1.newTab().setText("RS. 120"));
            tabLayout1.addTab(tabLayout1.newTab().setText("RS. 200"));

        }

        tabLayout1.addTab(tabLayout1.newTab().setText("OFFER"));

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                if (userData != null) {

                    ArrayList<UserPlan> alUserPlan = userData.getAlUserPlans();

                    if (alUserPlan != null) {


                        if (tab.getPosition() < 2) {

                            //Log.e("tababa", tab.getPosition()+"");
                            UserPlan userPlan = alUserPlan.get(tab.getPosition());


                            tvNoOfPhotographs.setText(userPlan.getNumberOfPictures().toString());
                            tvEditingIncluded.setText(userPlan.getEditingIncluded().toUpperCase());


                        }

                    }


                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void isFavouriteSuccessfully(boolean isFavourite) {
        EventBus.getDefault().postSticky(new UserFavorites(userData.getUserId(),isFavourite+""));

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
