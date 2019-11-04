package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupInterestFragment extends BaseFragment {

    @BindView(R.id.photoShootCB)
    CheckBox photoShootCB;

    @BindView(R.id.videoGraphyCB)
    CheckBox videoGraphyCB;

    SignupActivity activity;
    ArrayList<String> alUserInterest = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_interest, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();

        return retView;
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                alUserInterest.clear();

                if (photoShootCB.isChecked()) {
                    alUserInterest.add("PHOTO SHOOT");
                }

                if (videoGraphyCB.isChecked()) {
                    alUserInterest.add("VIDEOGRAPHY");
                }

                addInterestToUserdata();

                break;
        }
    }

    /**
     * method is used for adding interest data to User data.
     *
     * @param
     * @return
     */
    private void addInterestToUserdata() {
        User user = DataHandler.getInstance().getUser();
        user.setUserInterests(alUserInterest);

        activity.setViewPager(activity.nCurrentPageIndex + 1);
    }
}
