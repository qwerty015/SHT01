package com.autohubtraining.autohub.scene.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.events.CustomEvent;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.scene.photographer_detail.PhotographerDetail;
import com.autohubtraining.autohub.scene.profile.custom.FavouriteAdapter;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavouriteActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.lv_favourites)
    ListView lv_favourites;

    private ArrayList<User> al_favourites = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);

        User user = DataHandler.getInstance().getUser();

        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);

        lv_favourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User photographer = al_favourites.get(i);

                Intent intent = new Intent(FavouriteActivity.this, PhotographerDetail.class);
                intent.putExtra(AppConstants.key_photographer, photographer);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getFavouritePhotographers();
    }

    /**
     * method is used for getting favourite photographers from FirebaseFirestore.
     *
     * @param
     * @return
     */
    private void getFavouritePhotographers() {
        showLoading("");

        User user = DataHandler.getInstance().getUser();

        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).whereArrayContains("followings", user.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dismissLoading();

                if (task.isSuccessful()) {
                    al_favourites = new ArrayList<User>();

                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        User photographer = documentSnapshot.toObject(User.class);
                        al_favourites.add(photographer);
                    }

                    FavouriteAdapter adapter_favourites = new FavouriteAdapter(FavouriteActivity.this, al_favourites);
                    lv_favourites.setAdapter(adapter_favourites);
                } else {
                    Log.d(AppConstants.TAG, "Failed: " + task.getException());
                }
            }
        });
    }
}
