package com.autohubtraining.autohub.scene.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.profile.ProfileActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookingsFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.iv_new_bookings_ul)
    ImageView iv_new_bookings_ul;
    @BindView(R.id.iv_prev_bookings_ul)
    ImageView iv_prev_bookings_ul;
    @BindView(R.id.lv_new_bookings)
    ListView lv_new_bookings;
    @BindView(R.id.lv_prev_bookings)
    ListView lv_prev_bookings;

    private MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_bookings, container, false);
        ButterKnife.bind(this, retView);

        mainActivity = (MainActivity) getActivity();

        User user = DataHandler.getInstance().getUser();
        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);

        return retView;
    }

    @OnClick({R.id.layout_new_bookings, R.id.layout_prev_bookings, R.id.iv_avatar})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_avatar:
                Intent intent = new Intent(mainActivity, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_new_bookings:
                if (iv_new_bookings_ul.getVisibility() == View.GONE) {
                    iv_new_bookings_ul.setVisibility(View.VISIBLE);
                    lv_new_bookings.setVisibility(View.VISIBLE);

                    iv_prev_bookings_ul.setVisibility(View.GONE);
                    lv_prev_bookings.setVisibility(View.GONE);
                }
                break;
            case R.id.layout_prev_bookings:
                if (iv_prev_bookings_ul.getVisibility() == View.GONE) {
                    iv_new_bookings_ul.setVisibility(View.GONE);
                    lv_new_bookings.setVisibility(View.GONE);

                    iv_prev_bookings_ul.setVisibility(View.VISIBLE);
                    lv_prev_bookings.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
