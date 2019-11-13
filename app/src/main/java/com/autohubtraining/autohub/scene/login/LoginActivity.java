package com.autohubtraining.autohub.scene.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomButton;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.main.MainActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    CustomEditView email;
    @BindView(R.id.password)
    CustomEditView password;
    @BindView(R.id.nextBtn)
    CustomButton nextBtn;

    private FirebaseAuth mAuth;
    private String emailStr, passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        email.setText("mohamed.fouad0629@gmail.com");
        password.setText("123456");
    }

    @OnClick({R.id.visiblePwdBtn, R.id.forgot_password, R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.visiblePwdBtn:
                if (password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                break;
            case R.id.forgot_password:
                if (isValidateEmail()) {
                    sendPasswordResetEmail(emailStr);
                }

                break;
            case R.id.nextBtn:
                if (isValidate()) {
                    signInWithEmailAndPassword(emailStr, passwordStr);
                }
                break;
        }
    }

    /**
     * method is used for signing user on firebase.
     *
     * @param email, password
     * @return
     */
    private void signInWithEmailAndPassword(String email, String password) {
        showLoading("");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(AppConstants.TAG, "signInWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            getUserDataFromFirestore(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(AppConstants.TAG, "signInWithEmail:failure", task.getException());
                            AppUtils.showToast(task.getException().getMessage(), LoginActivity.this);

                            dismissLoading();
                        }

                        // ...
                    }
                });
    }

    /**
     * method is used for getting user data from firebase db.
     *
     * @param firebaseUser
     * @return
     */
    private void getUserDataFromFirestore(FirebaseUser firebaseUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection(AppConstants.ref_user).document(firebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    dismissLoading();

                    DocumentSnapshot doc = task.getResult();

                    if (doc.getData() != null) {
                        User user = doc.toObject(User.class);
                        DataHandler.getInstance().setUser(user);

                        MainActivity.startActivity(LoginActivity.this);
                    }
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                AppUtils.showToast(e.toString(), LoginActivity.this);
            }
        });
    }

    /**
     * method is used for sending email to reset password.
     *
     * @param email
     * @return
     */
    private void sendPasswordResetEmail(String email) {
        showLoading("");

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dismissLoading();

                        if (task.isSuccessful()) {
                            AppUtils.showToast("Email sent.", LoginActivity.this);
                        }
                    }
                });
    }

    /**
     * method is used for checking valid email address and password.
     *
     * @param
     * @return boolean true for valid false for invalid
     */
    private boolean isValidate() {
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailStr)) {
            AppUtils.showToast(getString(R.string.email_empty_error), this);
            return false;
        } else if (!AppUtils.isEmailValid(emailStr)) {
            AppUtils.showToast(getString(R.string.email_format_error), this);
            return false;
        } else if (TextUtils.isEmpty(passwordStr)) {
            AppUtils.showToast(getString(R.string.password_error), this);
            return false;
        }

        return true;
    }

    /**
     * method is used for checking valid email address.
     *
     * @param
     * @return boolean true for valid false for invalid
     */
    private boolean isValidateEmail() {
        emailStr = email.getText().toString().trim();

        if (TextUtils.isEmpty(emailStr)) {
            AppUtils.showToast(getString(R.string.email_empty_error), this);
            return false;
        } else if (!AppUtils.isEmailValid(emailStr)) {
            AppUtils.showToast(getString(R.string.email_format_error), this);
            return false;
        }

        return true;
    }
}
