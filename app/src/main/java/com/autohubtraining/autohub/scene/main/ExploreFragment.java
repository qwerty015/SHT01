package com.autohubtraining.autohub.scene.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.main.custom.ExplorePhotographerListAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExploreFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.layout_search)
    RelativeLayout layout_search;
    @BindView(R.id.ev_search)
    CustomEditView ev_search;
    @BindView(R.id.lv_photographers)
    ListView lv_photographers;

    private ArrayList<User> al_photographers = new ArrayList<User>();

    private MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, retView);

        mainActivity = (MainActivity) getActivity();

        User user = DataHandler.getInstance().getUser();

        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);

        getPhotographers();

        return retView;
    }

    @OnClick({R.id.iv_search})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_search:
                if (layout_search.getVisibility() == View.GONE) {
                    layout_search.setVisibility(View.VISIBLE);
                    ev_search.setText("");
                } else {
                    layout_search.setVisibility(View.GONE);
                }

                break;
        }
    }

    /**
     * method is used for getting photographers from FirebaseFirestore.
     *
     * @param
     * @return
     */
    private void getPhotographers() {
        showLoading("");

        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).whereEqualTo("type", AppConstants.PHOTOGRAPHER).addSnapshotListener((documentSnapshot, e) -> {
            dismissLoading();

            if (e != null) {
                Log.d(AppConstants.TAG, "Failed: " + e.getMessage());
            } else {
                Log.d(AppConstants.TAG, "Success:");
                if (documentSnapshot.getDocuments() != null) {
                    al_photographers = new ArrayList<User>();

                    for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                        User photographer = snapshot.toObject(User.class);

                        if (!photographer.getUserId().equals(DataHandler.getInstance().getUser().getUserId())) {
                            al_photographers.add(photographer);
                        }
                    }

                    ExplorePhotographerListAdapter adapter = new ExplorePhotographerListAdapter(mainActivity, al_photographers);
                    lv_photographers.setAdapter(adapter);
                }
            }
        });
    }
}
