package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupCameraInfoFragment extends BaseFragment {

    @BindView(R.id.editBio)
    EditText editBio;

    SignupActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_camera_info, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();

        return retView;
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                addBioToUserdata(editBio.getText().toString());
                break;
        }
    }

    /**
     * method is used for adding bio to User data.
     *
     * @param bio
     * @return
     */
    private void addBioToUserdata(String bio) {
        User user = DataHandler.getInstance().getUser();
        user.setBio(bio);

        UserData userData = DataHandler.getInstance().getUserData();
        userData.setBio(bio);

        activity.setViewPager(activity.nCurrentPageIndex + 1);
    }
}
