package com.autohubtraining.autohub.scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.login.LoginActivity;
import com.autohubtraining.autohub.scene.main.MainActivity;
import com.autohubtraining.autohub.scene.signup.SignupActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
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

public class LogoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);

        checkUserIsLogin();
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

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            this.showLoading("Loading");

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
