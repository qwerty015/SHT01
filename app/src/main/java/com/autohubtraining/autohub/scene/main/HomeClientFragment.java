package com.autohubtraining.autohub.scene.main;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.booking.GiveReviewActivity;
import com.autohubtraining.autohub.scene.profile.ProfileActivity;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeClientFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_location)
    TextView tv_location;

    private MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_home_client, container, false);
        ButterKnife.bind(this, retView);

        mainActivity = (MainActivity) getActivity();

        updateAvatar();
        setLocation();

        return retView;
    }

    @OnClick({R.id.iv_avatar, R.id.b_find})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_avatar:
                Intent intent = new Intent(mainActivity, ProfileActivity.class);
                startActivity(intent);

                break;
            case R.id.b_find:
                mainActivity.setViewPager(1);
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
     * method is used for setting user's location.
     *
     * @param
     * @return
     */
    public void setLocation() {
        User user = DataHandler.getInstance().getUser();

        if (user.getLocation() == null) {
            return;
        }

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mainActivity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(user.getLocation().getLatitude(), user.getLocation().getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getAdminArea();

            tv_location.setText(address);
        } catch (Exception e) {
        }
    }
}
