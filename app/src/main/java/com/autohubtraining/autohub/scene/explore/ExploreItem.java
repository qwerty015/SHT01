package com.autohubtraining.autohub.scene.explore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.photographer_detail.custom.PhotoViewPagerAdapter;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExploreItem extends RecyclerView.Adapter<ExploreItem.ViewHolder> {

    List<UserData> alUsers;
    Context context;
    int width;
    ItemClick itemClick;

    // RecyclerView recyclerView;
    public ExploreItem(Context context, List<UserData> user, int width, Fragment f) {
        this.alUsers = user;
        this.context = context;
        this.width = width;

        if (f != null) {
            itemClick = (ItemClick) f;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_image, parent, false);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
        listItem.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        UserData userData = alUsers.get(position);

        //LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_image, container, false);


        holder.tvName.setText(userData.getFirstName());
        if (userData.getUserCamera() != null && userData.getUserCamera().getCameraBrand() != null && userData.getUserCamera().getCameraModel() != null) {
            holder.tvCamera.setText(userData.getUserCamera().getCameraBrand().toUpperCase() + " " + userData.getUserCamera().getCameraModel());
        }


        Glide.with(context).load(userData.getAvatarUrl()).placeholder(R.mipmap.profile_image_icon).into(holder.ivPic);


        holder.btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.tabLayout.setupWithViewPager(holder.vpPager, true);
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(context, userData.getBestImages());
        holder.vpPager.setAdapter(adapter);
        holder.vpPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

               // Log.e("workingg", "workingngngg");
                itemClick.getAction(event.getAction());
                return false;
            }
        });


        if (userData.isFavourite()) {
            holder.ivFavourite.setImageResource(R.drawable.ic_fav_red);
        } else {
            holder.ivFavourite.setImageResource(R.drawable.heart);
        }
        holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!userData.isFavourite()) {
                    holder.ivFavourite.setImageResource(R.drawable.ic_fav_red);



                } else {
                    holder.ivFavourite.setImageResource(R.drawable.heart);


                }
                itemClick.setFavourite(!userData.isFavourite(), userData);
                userData.setFavourite(!userData.isFavourite());






            }
        });

    }


    @Override
    public int getItemCount() {
        return alUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewPager vpPager;
        CustomTextView tvName;
        TextView tvCamera;
        CircleImageView ivPic;
        Button btnViewMore;
        TabLayout tabLayout;
        LinearLayout root_container;
        ImageView ivFavourite;

        public ViewHolder(View linearLayout) {
            super(linearLayout);
            vpPager = (ViewPager) linearLayout.findViewById(R.id.vpPager);
            tvName = linearLayout.findViewById(R.id.tvName);
            tvCamera = linearLayout.findViewById(R.id.tvCamera);
            ivPic = linearLayout.findViewById(R.id.profile_image);
            btnViewMore = linearLayout.findViewById(R.id.btnViewMore);
            tabLayout = (TabLayout) linearLayout.findViewById(R.id.tab_layout);
            root_container = (LinearLayout) linearLayout.findViewById(R.id.root_container);
            ivFavourite = (ImageView) linearLayout.findViewById(R.id.ivFavourite);

        }
    }

    interface ItemClick {
        void getAction(int action);

        void setFavourite(boolean isFavourite, UserData userData);

    }





}
