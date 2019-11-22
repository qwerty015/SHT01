package com.autohubtraining.autohub.scene.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.booking.Booking;
import com.autohubtraining.autohub.data.model.booking.Feedback;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class GiveReviewActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_photographername)
    TextView tv_photographername;
    @BindView(R.id.rating_bar)
    MaterialRatingBar rating_bar;
    @BindView(R.id.et_review)
    EditText et_review;

    Booking booking;
    ArrayList<Feedback> al_feedback = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_give_review);
        ButterKnife.bind(this);

        if (getIntent().getExtras().containsKey(AppConstants.key_booking_id)) {
            String id = getIntent().getStringExtra(AppConstants.key_booking_id);

            getBooking(id);
        }
    }

    @OnClick({R.id.b_done})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.b_done:
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
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    booking = document.toObject(Booking.class);

                    Glide.with(GiveReviewActivity.this).load(booking.getPhotographerAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);
                    tv_photographername.setText(booking.getPhotographerName());

                    getPhotographerFeedbacks();
                } else {
                    dismissLoading();

                    Log.d(AppConstants.TAG, "Failed: " + task.getException());
                }
            }
        });
    }

    /**
     * method is used for getting photographer feedbacks.
     *
     * @param
     * @return
     */
    private void getPhotographerFeedbacks() {
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(booking.getPhotographerId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                dismissLoading();

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    User photographer = document.toObject(User.class);

                    if (photographer.getArrayFeedback() == null) {
                        al_feedback = new ArrayList<>();
                    } else {
                        al_feedback = photographer.getArrayFeedback();
                    }
                } else {
                    Log.d(AppConstants.TAG, "Failed: " + task.getException());
                }
            }
        });
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
        data.put("score", rating_bar.getRating());
        data.put("feedback", et_review.getText().toString());

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_booking).document(booking.getBookingId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Feedback feedback = new Feedback();

                feedback.setName(booking.getName());
                feedback.setPrice(booking.getPrice());
                feedback.setScore(rating_bar.getRating());
                feedback.setFeedback(et_review.getText().toString());

                al_feedback.add(feedback);

                saveFeedbackToPhotographer();
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

    /**
     * method is used for saving feedback to firestore.
     *
     * @param
     * @return
     */
    private void saveFeedbackToPhotographer() {
        Map<String, Object> data = new HashMap<>();
        data.put("arrayFeedback", al_feedback);

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(booking.getPhotographerId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
