package com.autohubtraining.autohub.scene.profile.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.util.AppUtils;

import java.util.ArrayList;

public class FavouriteAdapter extends BaseAdapter {

    Context context;
    ArrayList<User> alFavourite = new ArrayList<>();

    public FavouriteAdapter(Context context, ArrayList<User> alFavourite) {
        this.context = context;
        this.alFavourite = alFavourite;
    }

    @Override
    public int getCount() {
        return alFavourite.size();
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
        TextView tv_name;
        TextView tv_distance;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FavouriteAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new FavouriteAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_favourite, parent, false);

            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_distance = convertView.findViewById(R.id.tv_distance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FavouriteAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        final User photographer = alFavourite.get(viewHolder.position);

        viewHolder.tv_name.setText(photographer.getFirstName() + " " + photographer.getLastName());

        float distance = AppUtils.getDistance(DataHandler.getInstance().getUser().getLocation(), photographer.getLocation());
        viewHolder.tv_distance.setText(String.format("%.1f KMS AWAY", distance));

        return convertView;
    }
}

