package com.autohubtraining.autohub.scene.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.model.booking.Booking;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookingReceivedActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_clientname)
    TextView tv_clientname;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_camerainfo)
    TextView tv_camerainfo;
    @BindView(R.id.tv_bookingname)
    TextView tv_bookingname;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.b_scan_code)
    Button b_scan_code;

    Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking_received);
        ButterKnife.bind(this);

        if (getIntent().getExtras().containsKey(AppConstants.key_booking_id)) {
            String id = getIntent().getStringExtra(AppConstants.key_booking_id);
            getBooking(id);
        }
    }

    @OnClick({R.id.b_scan_code})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.b_scan_code:
                setBookingStatus(AppConstants.BOOKING_COMPLETED);
                break;
        }
    }

    /**
     * method is used for getting booking data from FirebaseFirestore.
     *
     * @param id documentId of photographer
     * @return
     */
    public void getBooking(String id) {
        showLoading("");

        FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                dismissLoading();

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    booking = document.toObject(Booking.class);

                    showBookingData();
                } else {
                    Log.d(AppConstants.TAG, "Failed: " + task.getException());
                }
            }
        });
    }

    /**
     * method is used for showing booking data to view.
     *
     * @param
     * @return
     */
    private void showBookingData() {
        Glide.with(this).load(booking.getClientAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
        tv_clientname.setText(booking.getClientName());
        tv_distance.setText(booking.getDistance() + " KMS AWAY");
        tv_camerainfo.setText(booking.getCameraInfo());
        tv_bookingname.setText(booking.getName());
        tv_price.setText("$" + booking.getPrice());

        if (booking.getStatus() == AppConstants.BOKKING_NEW) {
            b_scan_code.setVisibility(View.VISIBLE);
        } else {
            b_scan_code.setVisibility(View.GONE);
        }
    }

    /**
     * method is used for saving booking status to firestore.
     *
     * @param status status of booking
     * @return
     */
    private void setBookingStatus(int status) {
        showLoading("");

        Map<String, Object> data = new HashMap<>();
        data.put("status", status);

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).document(booking.getBookingId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();

                showErrorToast(e.getMessage());
                Log.e(AppConstants.TAG, "data failed with an exception" + e.toString());
            }
        });
    }
}
