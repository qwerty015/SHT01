package com.autohubtraining.autohub.scene;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.booking.BookingReceivedActivity;
import com.autohubtraining.autohub.scene.booking.GiveReviewActivity;
import com.autohubtraining.autohub.scene.login.LoginActivity;
import com.autohubtraining.autohub.scene.main.MainActivity;
import com.autohubtraining.autohub.scene.signup.SignupActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class LogoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);

        Fabric.with(this, new Crashlytics());

        checkUserIsLogin();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    @OnClick({R.id.login, R.id.signup})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.signup:
                startActivity(new Intent(this, SignupActivity.class));
                break;
        }
    }

    /**
     * method is used for checking user is login.
     *
     * @param
     * @return
     */
    private void checkUserIsLogin() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            this.showLoading("");

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference documentReference = db.collection(AppConstants.ref_user).document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        LogoActivity.this.dismissLoading();

                        DocumentSnapshot doc = task.getResult();

                        if (doc.getData() != null) {
                            User user = doc.toObject(User.class);
                            DataHandler.getInstance().setUser(user);

                            MainActivity.startActivity(LogoActivity.this);
                        }
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    LogoActivity.this.dismissLoading();
                    AppUtils.showToast(e.toString(), LogoActivity.this);
                }
            });
        }
    }
}
