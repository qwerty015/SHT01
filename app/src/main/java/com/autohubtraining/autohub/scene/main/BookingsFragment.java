package com.autohubtraining.autohub.scene.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.booking.Booking;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.booking.BookingDoneActivity;
import com.autohubtraining.autohub.scene.booking.BookingReceivedActivity;
import com.autohubtraining.autohub.scene.booking.GiveReviewActivity;
import com.autohubtraining.autohub.scene.main.custom.BookingListAdapter;
import com.autohubtraining.autohub.scene.photographer_detail.PhotographerDetail;
import com.autohubtraining.autohub.scene.profile.FavouriteActivity;
import com.autohubtraining.autohub.scene.profile.ProfileActivity;
import com.autohubtraining.autohub.scene.profile.custom.FavouriteAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
    private ArrayList<Booking> al_bookings_new = new ArrayList<>();
    private ArrayList<Booking> al_bookings_prev = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_bookings, container, false);
        ButterKnife.bind(this, retView);

        mainActivity = (MainActivity) getActivity();

        updateAvatar();
        getBookingData();

        lv_new_bookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Booking booking = al_bookings_new.get(i);

                if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT) {
                    Intent intent = new Intent(mainActivity, BookingDoneActivity.class);
                    intent.putExtra(AppConstants.key_booking, booking);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mainActivity, BookingReceivedActivity.class);
                    intent.putExtra(AppConstants.key_booking, booking);
                    startActivity(intent);
                }
            }
        });

        lv_prev_bookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Booking booking = al_bookings_prev.get(i);

                if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT) {
                    if (booking.getStatus() == AppConstants.BOOKING_COMPLETED && booking.getFeedback() == null) {
                        Intent intent = new Intent(mainActivity, GiveReviewActivity.class);
                        intent.putExtra(AppConstants.key_booking, booking);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mainActivity, BookingDoneActivity.class);
                        intent.putExtra(AppConstants.key_booking, booking);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(mainActivity, BookingReceivedActivity.class);
                    intent.putExtra(AppConstants.key_booking, booking);
                    startActivity(intent);
                }
            }
        });

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
     * method is used for getting booking data
     *
     * @param
     * @return
     */
    private void getBookingData() {
        showLoading("");

        User user = DataHandler.getInstance().getUser();

        if (user.getType() == AppConstants.CLIENT) {
            FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).whereEqualTo("clientId", user.getUserId()).addSnapshotListener((documentSnapshot, e) -> {
                dismissLoading();

                if (e != null) {
                    Log.d(AppConstants.TAG, "Failed: " + e.getMessage());
                } else {
                    Log.d(AppConstants.TAG, "Success:");
                    if (documentSnapshot.getDocuments() != null) {
                        al_bookings_new = new ArrayList<>();
                        al_bookings_prev = new ArrayList<>();

                        for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                            Booking booking = snapshot.toObject(Booking.class);

                            if (booking.getStatus() == AppConstants.BOKKING_NEW) {
                                al_bookings_new.add(booking);
                            } else {
                                al_bookings_prev.add(booking);
                            }
                        }

                        BookingListAdapter adapter_bookings_new = new BookingListAdapter(mainActivity, al_bookings_new);
                        lv_new_bookings.setAdapter(adapter_bookings_new);

                        BookingListAdapter adapter_bookings_prev = new BookingListAdapter(mainActivity, al_bookings_prev);
                        lv_prev_bookings.setAdapter(adapter_bookings_prev);
                    }
                }
            });
        } else {
            FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).whereEqualTo("photographerId", user.getUserId()).addSnapshotListener((documentSnapshot, e) -> {
                dismissLoading();

                if (e != null) {
                    Log.d(AppConstants.TAG, "Failed: " + e.getMessage());
                } else {
                    Log.d(AppConstants.TAG, "Success:");
                    if (documentSnapshot.getDocuments() != null) {
                        al_bookings_new = new ArrayList<>();
                        al_bookings_prev = new ArrayList<>();

                        for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                            Booking booking = snapshot.toObject(Booking.class);

                            if (booking.getStatus() == AppConstants.BOKKING_NEW) {
                                al_bookings_new.add(booking);
                            } else {
                                al_bookings_prev.add(booking);
                            }
                        }

                        BookingListAdapter adapter_bookings_new = new BookingListAdapter(mainActivity, al_bookings_new);
                        lv_new_bookings.setAdapter(adapter_bookings_new);

                        BookingListAdapter adapter_bookings_prev = new BookingListAdapter(mainActivity, al_bookings_prev);
                        lv_prev_bookings.setAdapter(adapter_bookings_prev);
                    }
                }
            });
        }
    }
}
