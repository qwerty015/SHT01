package com.autohubtraining.autohub.scene.photographer_detail.custom;

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

public class PhotoViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> alBestPhotos = new ArrayList<>();

    public PhotoViewPagerAdapter(Context context, List<String> bestPhotos) {
        this.context = context;

        if (bestPhotos != null) {
            alBestPhotos = bestPhotos;
        }
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo, collection, false);
        ImageView iv_photo = layout.findViewById(R.id.iv_photo);

        if (alBestPhotos.size() > 0) {
            Glide.with(context).load(alBestPhotos.get(position)).into(iv_photo);
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
        return alBestPhotos.size();
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
