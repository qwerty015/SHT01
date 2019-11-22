package com.autohubtraining.autohub.scene.photographer_detail;

import android.content.Intent;
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
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.booking.Booking;
import com.autohubtraining.autohub.data.model.booking.Feedback;
import com.autohubtraining.autohub.data.model.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.booking.BookingDoneActivity;
import com.autohubtraining.autohub.scene.photographer_detail.custom.PhotoViewPagerAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

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
    @BindView(R.id.tv_package_reviews_count)
    TextView tv_package_reviews_count;
    @BindView(R.id.tv_package_rating)
    TextView tv_package_rating;

    private User photographer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photographer_detail);
        ButterKnife.bind(this);

        if (getIntent().getExtras().containsKey(AppConstants.key_photographer_id)) {
            String id = getIntent().getStringExtra(AppConstants.key_photographer_id);

            getPhotographer(id);
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
                if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT && tab_plan.getSelectedTabPosition() < 2) {
                    createNewBooking();
                }

                break;
        }
    }

    /**
     * method is used for getting photographer from FirebaseFirestore.
     *
     * @param id documentId of photographer
     * @return
     */
    public void getPhotographer(String id) {
        showLoading("");

        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                dismissLoading();

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    photographer = document.toObject(User.class);

                    showPhotographerData();
                } else {
                    Log.d(AppConstants.TAG, "Failed: " + task.getException());
                }
            }
        });
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

        if (photographer.getArrayFeedback() != null) {
            float ratingSum = 0;

            for (int i = 0; i < photographer.getArrayFeedback().size(); i++) {
                Feedback feedback = photographer.getArrayFeedback().get(i);
                ratingSum += feedback.getScore();
            }

            if (photographer.getArrayFeedback().size() > 0) {
                tv_rating.setText(String.format("%.1f", ratingSum / photographer.getArrayFeedback().size()));
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

        if (photographer.getArrayPlan() != null) {
            UserPlan userPlan = photographer.getArrayPlan().get(0);

            tv_planname.setText(userPlan.getPlanName());
            tv_numpictures.setText(userPlan.getNumberOfPictures());

            if (userPlan.getEditingIncluded().equals("true")) {
                tv_editingincluded.setText("YES");
            } else {
                tv_editingincluded.setText("NO");
            }

            b_book_package.setText(R.string.book_this_package);

            if (photographer.getArrayFeedback() != null) {
                float ratingSum = 0;
                int count = 0;

                for (int i = 0; i < photographer.getArrayFeedback().size(); i++) {
                    Feedback feedback = photographer.getArrayFeedback().get(i);

                    if (userPlan.getPlanName().equals(feedback.getName())) {
                        ratingSum += feedback.getScore();
                        count++;
                    }
                }

                if (count > 0) {
                    tv_package_reviews_count.setText(count + " REVIEWS");
                    tv_package_rating.setText(String.format("%.1f", ratingSum / count));
                }
            }
        }

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

                        if (photographer.getArrayFeedback() != null) {
                            float ratingSum = 0;
                            int count = 0;

                            for (int i = 0; i < photographer.getArrayFeedback().size(); i++) {
                                Feedback feedback = photographer.getArrayFeedback().get(i);

                                if (userPlan.getPlanName().equals(feedback.getName())) {
                                    ratingSum += feedback.getScore();
                                    count++;
                                }
                            }

                            if (count > 0) {
                                tv_package_reviews_count.setText(count + " REVIEWS");
                                tv_package_rating.setText(String.format("%.1f", ratingSum / count));
                            }
                        }
                    } else {
                        tv_planname.setText("FREE SHOOT");
                        tv_numpictures.setText("0");
                        tv_editingincluded.setText("NO");
                        b_book_package.setText(R.string.redeem_offer);
                        tv_package_reviews_count.setText("0 REVIEWS");
                        tv_package_rating.setText("0.0");
                    }
                } else {
                    tv_planname.setText("FREE SHOOT");
                    tv_numpictures.setText("0");
                    tv_editingincluded.setText("NO");
                    b_book_package.setText(R.string.redeem_offer);
                    tv_package_reviews_count.setText("0 REVIEWS");
                    tv_package_rating.setText("0.0");
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

    /**
     * method is used for creating new booing to firestore.
     *
     * @param
     * @return
     */
    private void createNewBooking() {
        showLoading("");

        User user = DataHandler.getInstance().getUser();
        UserPlan userPlan = photographer.getArrayPlan().get(tab_plan.getSelectedTabPosition());

        Booking booking = new Booking();
        booking.setStatus(AppConstants.BOKKING_NEW);
        booking.setTimestamp(new Date());
        booking.setName(userPlan.getPlanName());
        booking.setPrice(userPlan.getPrice());
        booking.setDistance(String.format("%.1f" ,AppUtils.getDistance(photographer.getLocation(), user.getLocation())));
        booking.setPhotographerId(photographer.getUserId());
        booking.setPhotographerName(photographer.getFirstName() + " " + photographer.getLastName());
        booking.setPhotographerAvatarUrl(photographer.getAvatarUrl());
        booking.setCameraInfo(photographer.getCameraBrand() + " " + photographer.getCameraModel());
        booking.setClientId(user.getUserId());
        booking.setClientName(user.getFirstName() + " " + user.getLastName());
        booking.setClientAvatarUrl(user.getAvatarUrl());

        String documentId = FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).document().getId();
        booking.setBookingId(documentId);

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).document(documentId).set(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                Intent intent = new Intent(PhotographerDetail.this, BookingDoneActivity.class);
                intent.putExtra(AppConstants.key_booking, booking);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                showErrorToast(e.toString());
                Log.e("firestore", "data failed with an exception" + e.toString());
            }
        });
    }

}
