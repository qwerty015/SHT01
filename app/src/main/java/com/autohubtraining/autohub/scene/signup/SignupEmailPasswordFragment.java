package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupEmailPasswordFragment extends BaseFragment {

    @BindView(R.id.email)
    CustomEditView email;
    @BindView(R.id.password)
    CustomEditView password;
    @BindView(R.id.confirm_password)
    CustomEditView confirm_password;
    @BindView(R.id.confirm_password_badge)
    Button confirm_password_badge;

    SignupActivity activity;
    String emailStr, passwordStr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_email_password, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordStr = password.getText().toString().trim();
                String confirmPwdStr = confirm_password.getText().toString().trim();

                if (passwordStr.length() > 0 && passwordStr.equals(confirmPwdStr)) {
                    confirm_password_badge.setVisibility(View.VISIBLE);
                } else {
                    confirm_password_badge.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordStr = password.getText().toString().trim();
                String confirmPwdStr = confirm_password.getText().toString().trim();

                if (passwordStr.length() > 0 && passwordStr.equals(confirmPwdStr)) {
                    confirm_password_badge.setVisibility(View.VISIBLE);
                } else {
                    confirm_password_badge.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        email.setText("mohamed.fouad0629@gmail.com");
        password.setText("123456");
        confirm_password.setText("123456");

        return retView;
    }

    @OnClick({R.id.nextBtn, R.id.visiblePwdBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.visiblePwdBtn:
                if (password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.nextBtn:
                if (isValidate()) {
                    createUserWithEmailAndPassword(emailStr, passwordStr);
                }
                break;
        }
    }

    /**
     * method is used for creating user on firebase.
     *
     * @param email, password
     * @return
     */
    private void createUserWithEmailAndPassword(String email, String password) {
        showLoading("Loading");
        activity.getFirebaseAuthInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(AppConstants.TAG, "createUserWithEmail:success");
                            FirebaseUser user = activity.getFirebaseAuthInstance().getCurrentUser();
                            addUserToFirestore(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            dismissLoading();
                            Log.w(AppConstants.TAG, "createUserWithEmail:failure", task.getException());
                            AppUtils.showToast(task.getException().getMessage(), activity);
                        }
                    }
                });
    }

    /**
     * method is used for adding user on firebase db.
     *
     * @param firebaseUser
     * @return
     */
    private void addUserToFirestore(FirebaseUser firebaseUser) {
        User user = new User();
        user.setUserId(firebaseUser.getUid());
        user.setType(DataHandler.getInstance().getUserType());
        user.setFirstName(activity.str_firstname);
        user.setLastName(activity.str_lastname);
        user.setEmail(firebaseUser.getEmail());

        activity.getFirebaseDB().collection(AppConstants.ref_user).document(user.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists() && !documentSnapshot.getData().isEmpty()) {
                    AppUtils.showToast("Users Already Registred. Please Login", activity);
                } else {
                    activity.getFirebaseDB().collection(AppConstants.ref_user).document(firebaseUser.getUid()).set(user);
                    DataHandler.getInstance().setUser(user);

                    UserData userData = new UserData();
                    userData.setUserId(user.getUserId());
                    userData.setType(user.getType());
                    userData.setFirstName(user.getFirstName());
                    userData.setLastName(user.getLastName());
                    userData.setEmail(user.getEmail());
                    DataHandler.getInstance().setUserData(userData);

                    activity.setViewPager(activity.nCurrentPageIndex + 1);
                }

                dismissLoading();
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
        String confirmPwdStr = confirm_password.getText().toString().trim();

        if (TextUtils.isEmpty(emailStr)) {
            AppUtils.showToast(getString(R.string.email_empty_error), activity);
            return false;
        } else if (!AppUtils.isEmailValid(emailStr)) {
            AppUtils.showToast(getString(R.string.email_format_error), activity);
            return false;
        } else if (TextUtils.isEmpty(passwordStr)) {
            AppUtils.showToast(getString(R.string.password_error), getActivity());
            return false;
        } else if (!passwordStr.equals(confirmPwdStr)) {
            AppUtils.showToast(getString(R.string.password_different_with_confirm), getActivity());
            return false;
        }

        return true;
    }

}
