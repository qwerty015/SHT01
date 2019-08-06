package com.autohubtraining.autohub.scene.editprofile;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.autohubtraining.autohub.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private Context context;


    private List<Integer> images;

    List<String> alBestImages = new ArrayList<>();


    public MyViewPagerAdapter(Context context, List<String> bestImages) {
        this.context = context;
        images = new ArrayList<>();
        images.add(R.drawable.dummy4);
        images.add(R.mipmap.dummy2);

        if (bestImages != null) {
            alBestImages = bestImages;
        }

    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.details_image, collection, false);
        ImageView pagerImage = layout.findViewById(R.id.pagerImage);


        if (alBestImages.size() == 0) {

            Glide.with(context).load(images.get(position)).into(pagerImage);
        } else {
            Glide.with(context).load(alBestImages.get(position)).into(pagerImage);
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        if (alBestImages.size() == 0) {
            return images.size();
        } else {
            return alBestImages.size();
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }




    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

