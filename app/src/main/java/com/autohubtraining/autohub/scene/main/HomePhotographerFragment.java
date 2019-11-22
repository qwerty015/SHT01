package com.autohubtraining.autohub.scene.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.booking.Booking;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.profile.ProfileActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomePhotographerFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_earnings)
    TextView tv_earnings;
    @BindView(R.id.tv_upcomingshoots)
    TextView tv_upcomingshoots;
    @BindView(R.id.layout_upcomingshoots)
    RelativeLayout layout_upcomingshoots;

    private MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_home_photographer, container, false);
        ButterKnife.bind(this, retView);

        mainActivity = (MainActivity) getActivity();

        updateAvatar();

        return retView;
    }

    @OnClick({R.id.iv_avatar, R.id.layout_upcomingshoots})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_avatar:
                Intent intent = new Intent(mainActivity, ProfileActivity.class);
                startActivity(intent);

                break;
            case R.id.layout_upcomingshoots:
                mainActivity.bookingsFragment.showNewBookingList();
                mainActivity.setViewPager(2);
                break;
        }
    }

    /**
     * method is used for updating user's photo.
     *
     * @param
     * @return
     */
    public void updateAvatar() {
        User user = DataHandler.getInstance().getUser();
        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
    }

    /**
     * method is used for updating home data.
     *
     * @param
     * @return
     */
    public void updateData() {
        float score = 0;
        for (int i = 0; i < mainActivity.al_bookings_prev.size(); i++) {
            Booking booking = mainActivity.al_bookings_prev.get(i);

            if (booking.getStatus() == AppConstants.BOOKING_COMPLETED) {
                score += Float.parseFloat(booking.getPrice());
            }
        }

        tv_earnings.setText(String.format("$ %.1f", score));
        tv_upcomingshoots.setText("" + mainActivity.al_bookings_new.size());
    }
}
