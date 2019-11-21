package com.autohubtraining.autohub.scene.main;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.main.custom.ExplorePhotographerListAdapter;
import com.autohubtraining.autohub.scene.photographer_detail.PhotographerDetail;
import com.autohubtraining.autohub.scene.profile.ProfileActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.AppUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.lv_photographers)
    ListView lv_photographers;

    private ArrayList<User> al_photographers = new ArrayList<User>();
    ExplorePhotographerListAdapter adapter_photographers;

    private MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, retView);

        mainActivity = (MainActivity) getActivity();
        adapter_photographers = new ExplorePhotographerListAdapter(mainActivity, al_photographers);

        updateAvatar();
        setLocation();

        if (DataHandler.getInstance().getUser().getLocation() == null) {
            mainActivity.requestLocation();
        } else {
            getPhotographers();
        }

        ev_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter_photographers.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        lv_photographers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User photographer = al_photographers.get(i);

                Intent intent = new Intent(mainActivity, PhotographerDetail.class);
                intent.putExtra(AppConstants.key_photographer_id, photographer.getUserId());
                startActivity(intent);
            }
        });

        return retView;
    }

    @OnClick({R.id.iv_search, R.id.iv_avatar})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_search:
                if (layout_search.getVisibility() == View.GONE) {
                    layout_search.setVisibility(View.VISIBLE);
                    AppUtils.showKeyboard(mainActivity, ev_search);
                } else {
                    layout_search.setVisibility(View.GONE);
                    ev_search.setText("");
                    AppUtils.hideKeyboard(mainActivity, ev_search);
                }

                break;
            case R.id.iv_avatar:
                Intent intent = new Intent(mainActivity, ProfileActivity.class);
                startActivity(intent);

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
     * method is used for setting user's location.
     *
     * @param
     * @return
     */
    public void setLocation() {
        User user = DataHandler.getInstance().getUser();

        if (user.getLocation() == null) {
            return;
        }

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mainActivity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(user.getLocation().getLatitude(), user.getLocation().getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address;

            if (addresses.get(0).getSubLocality() == null) {
                address = addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
            } else {
                address = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getAdminArea();
            }

            tv_location.setText(address);
        } catch (Exception e) {
        }
    }

    /**
     * method is used for getting photographers from FirebaseFirestore.
     *
     * @param
     * @return
     */
    public void getPhotographers() {
        showLoading("");

        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).whereEqualTo("type", AppConstants.PHOTOGRAPHER).addSnapshotListener((documentSnapshot, e) -> {
            dismissLoading();

            if (e != null) {
                Log.d(AppConstants.TAG, "Failed: " + e.getMessage());
            } else {
                Log.d(AppConstants.TAG, "Success:");
                if (documentSnapshot.getDocuments() != null) {
                    al_photographers = new ArrayList<User>();

                    int index = 0;
                    for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                        User photographer = snapshot.toObject(User.class);

                        if (!photographer.getUserId().equals(DataHandler.getInstance().getUser().getUserId())) {
                            al_photographers.add(photographer);

                            Collections.sort(al_photographers.get(index).getArrayPlan(), new Comparator<UserPlan>() {
                                @Override
                                public int compare(UserPlan p1, UserPlan p2) {
                                    return Float.compare(Float.parseFloat(p1.getPrice()), Float.parseFloat(p2.getPrice()));
                                }
                            });
                            index++;
                        }
                    }

                    adapter_photographers = new ExplorePhotographerListAdapter(mainActivity, al_photographers);
                    adapter_photographers.getFilter().filter(ev_search.getText().toString());
                    lv_photographers.setAdapter(adapter_photographers);
                }
            }
        });
    }
}
