package com.autohubtraining.autohub.scene.photographer_detail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.events.CustomEvent;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.photographer_detail.custom.PhotoViewPagerAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PhotographerDetail extends BaseActivity {

    @BindView(R.id.photo_view_pager)
    ViewPager photo_view_pager;
    @BindView(R.id.photo_view_indicator)
    TabLayout photo_view_indicator;
    @BindView(R.id.iv_favourite)
    ImageView iv_favourite;
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_camerainfo)
    TextView tv_camerainfo;
    @BindView(R.id.tv_rank)
    TextView tv_rank;
    @BindView(R.id.tv_rating)
    TextView tv_rating;
    @BindView(R.id.tv_bio)
    TextView tv_bio;
    @BindView(R.id.tv_readmore)
    TextView tv_readmore;
    @BindView(R.id.tv_equipment)
    TextView tv_equipment;
    @BindView(R.id.tab_plan)
    TabLayout tab_plan;
    @BindView(R.id.tv_planname)
    TextView tv_planname;
    @BindView(R.id.tv_numpictures)
    TextView tv_numpictures;
    @BindView(R.id.tv_editingincluded)
    TextView tv_editingincluded;
    @BindView(R.id.b_book_package)
    Button b_book_package;

    private User photographer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photographer_detail);
        ButterKnife.bind(this);

        if (getIntent().getExtras().containsKey(AppConstants.key_photographer)) {
            photographer = (User) getIntent().getSerializableExtra(AppConstants.key_photographer);
            showPhotographerData();
        }
    }

    @OnClick({R.id.iv_favourite, R.id.tv_readmore, R.id.b_book_package})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_favourite:
                if (isFavouritePhotographer()) {
                    iv_favourite.setImageResource(R.drawable.heart);
                    setFavouriteData(false);
                } else {
                    iv_favourite.setImageResource(R.drawable.ic_fav_red);
                    setFavouriteData(true);
                }

                break;
            case R.id.tv_readmore:
                tv_readmore.setVisibility(View.GONE);
                tv_equipment.setVisibility(View.VISIBLE);
                break;
            case R.id.b_book_package:
                break;
        }
    }

    /**
     * method is used for showing photographer data to view.
     *
     * @param
     * @return
     */
    private void showPhotographerData() {
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(this, photographer.getBestImages());
        photo_view_pager.setAdapter(adapter);
        photo_view_indicator.setupWithViewPager(photo_view_pager, true);

        if (isFavouritePhotographer()) {
            iv_favourite.setImageResource(R.drawable.ic_fav_red);
        } else {
            iv_favourite.setImageResource(R.drawable.heart);
        }

        Glide.with(this).load(photographer.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
        tv_name.setText(photographer.getFirstName() + " " + photographer.getLastName());
        tv_camerainfo.setText(photographer.getCameraBrand() + " " + photographer.getCameraModel());
        tv_bio.setText(photographer.getBio());

        if (photographer.getCameraAccessories() != null) {
            for (int i = 0; i < photographer.getCameraAccessories().size(); i++) {
                String equip = photographer.getCameraAccessories().get(i);
                tv_equipment.setText(tv_equipment.getText().toString() + "\n" + equip);
            }
        }

        addPlanTabLayout();
    }

    /**
     * method is used for adding tabs of photographer's service plans.
     *
     * @param
     * @return
     */
    private void addPlanTabLayout() {
        if (photographer.getArrayPlan() != null) {
            for (int i = 0; i < photographer.getArrayPlan().size(); i++) {
                UserPlan userPlan = photographer.getArrayPlan().get(i);
                tab_plan.addTab(tab_plan.newTab().setText("$ " + userPlan.getPrice()));
            }
        }

        tab_plan.addTab(tab_plan.newTab().setText("OFFER"));

        tab_plan.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (photographer.getArrayPlan() != null) {
                    if (tab.getPosition() < photographer.getArrayPlan().size()) {
                        UserPlan userPlan = photographer.getArrayPlan().get(tab.getPosition());

                        tv_planname.setText(userPlan.getPlanName());
                        tv_numpictures.setText(userPlan.getNumberOfPictures());

                        if (userPlan.getEditingIncluded().equals("true")) {
                            tv_editingincluded.setText("YES");
                        } else {
                            tv_editingincluded.setText("NO");
                        }

                        b_book_package.setText(R.string.book_this_package);
                    } else {
                        tv_planname.setText("FREE SHOOT");
                        tv_numpictures.setText("0");
                        tv_editingincluded.setText("NO");
                        b_book_package.setText(R.string.redeem_offer);
                    }
                } else {
                    tv_planname.setText("FREE SHOOT");
                    tv_numpictures.setText("0");
                    tv_editingincluded.setText("NO");
                    b_book_package.setText(R.string.redeem_offer);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    /**
     * method is used for checking if photographer is favourite or not.
     *
     * @param
     * @return boolean true for favourite or false for not
     */
    private Boolean isFavouritePhotographer() {
        User user = DataHandler.getInstance().getUser();

        if (photographer.getFollowings() != null) {
            if (photographer.getFollowings().contains(user.getUserId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * method is used for saving user favourite data to firestore.
     *
     * @param isAdd
     * @return
     */
    private void setFavouriteData(Boolean isAdd) {
        User user = DataHandler.getInstance().getUser();

        if (isAdd) {
            if (photographer.getFollowings() != null) {
                photographer.getFollowings().add(user.getUserId());
            } else {
                ArrayList<String> alFollowings = new ArrayList<>();
                alFollowings.add(user.getUserId());
                photographer.setFollowings(alFollowings);
            }
        } else {
            if (photographer.getFollowings() != null) {
                photographer.getFollowings().remove(user.getUserId());
            } else {
                ArrayList<String> alFollowings = new ArrayList<>();
                photographer.setFollowings(alFollowings);
            }
        }

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(photographer.getUserId()).set(photographer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(AppConstants.TAG, "Success: favourite");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(AppConstants.TAG, "data failed with an exception" + e.toString());
            }
        });
    }

}
