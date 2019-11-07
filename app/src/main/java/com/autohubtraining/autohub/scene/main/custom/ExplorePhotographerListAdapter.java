package com.autohubtraining.autohub.scene.main.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ExplorePhotographerListAdapter extends BaseAdapter {

    private ArrayList<User> listPhotographers;

    public ExplorePhotographerListAdapter(Context context, ArrayList<User> list) {
        this.listPhotographers = list;
    }

    @Override
    public int getCount() {
        return listPhotographers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        int position;
        ImageView iv_photo;
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_camerainfo;
        TextView tv_price;
        TextView tv_numpictures;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ExplorePhotographerListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ExplorePhotographerListAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_explore_photographer, parent, false);

            viewHolder.iv_photo = convertView.findViewById(R.id.iv_photo);
            viewHolder.iv_avatar = convertView.findViewById(R.id.iv_avatar);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_camerainfo = convertView.findViewById(R.id.tv_camerainfo);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            viewHolder.tv_numpictures = convertView.findViewById(R.id.tv_numpictures);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ExplorePhotographerListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        final User photographer = listPhotographers.get(viewHolder.position);

        if (photographer.getBestImages().size() > 0) {
            Glide.with(parent.getContext()).load(photographer.getBestImages().get(0)).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.black_bg).into(viewHolder.iv_photo);
        }

        Glide.with(parent.getContext()).load(photographer.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(viewHolder.iv_avatar);
        viewHolder.tv_name.setText(photographer.getFirstName());
        viewHolder.tv_camerainfo.setText(photographer.getCameraBrand() + " " + photographer.getCameraModel());

        UserPlan plan = Collections.min(photographer.getArrayPlan(), new PlanComp());

        viewHolder.tv_price.setText("$ " + plan.getPrice() + " /");
        viewHolder.tv_numpictures.setText(plan.getNumberOfPictures() + " PICTURES");

        return convertView;
    }
}

class PlanComp implements Comparator<UserPlan>{
    @Override
    public int compare(UserPlan p1, UserPlan p2) {
        return Float.compare(Float.parseFloat(p1.getPrice()), Float.parseFloat(p2.getPrice()));
    }
}
