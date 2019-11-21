package com.autohubtraining.autohub.scene.main.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user_plan.UserPlan;
import com.autohubtraining.autohub.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ExplorePhotographerListAdapter extends BaseAdapter implements Filterable {

    private ArrayList<User> listFilter;
    private final ArrayList<User> listPhotographers = new ArrayList<>();

    public ExplorePhotographerListAdapter(Context context, ArrayList<User> list) {
        this.listFilter = list;
        this.listPhotographers.addAll(list);
    }

    @Override
    public int getCount() {
        return listFilter.size();
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
        TextView tv_distance;
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
            viewHolder.tv_distance = convertView.findViewById(R.id.tv_distance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ExplorePhotographerListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        final User photographer = listFilter.get(viewHolder.position);

        if (photographer.getBestImages().size() > 0) {
            Glide.with(parent.getContext()).load(photographer.getBestImages().get(0)).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).placeholder(R.drawable.black_bg).into(viewHolder.iv_photo);
        }

        Glide.with(parent.getContext()).load(photographer.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(viewHolder.iv_avatar);
        viewHolder.tv_name.setText(photographer.getFirstName());
        viewHolder.tv_camerainfo.setText(photographer.getCameraBrand() + " " + photographer.getCameraModel());

        UserPlan plan = photographer.getArrayPlan().get(0);
        viewHolder.tv_price.setText("$ " + plan.getPrice() + " /");
        viewHolder.tv_numpictures.setText(plan.getNumberOfPictures() + " PICTURES");

        float distance = AppUtils.getDistance(DataHandler.getInstance().getUser().getLocation(), photographer.getLocation());
        viewHolder.tv_distance.setText(String.format("%.1f KM", distance));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) { // if your editText field is empty, return full list of FriendItem
                    results.count = listPhotographers.size();
                    results.values = listPhotographers;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();

                    constraint = constraint.toString().toLowerCase(); // if we ignore case
                    for (User item : listPhotographers) {
                        String firstName = item.getFirstName().toLowerCase(); // if we ignore case
                        String lastName = item.getLastName().toLowerCase(); // if we ignore case
                        if (firstName.contains(constraint.toString()) || lastName.contains(constraint.toString())) {
                            filteredList.add(item); // added item witch contains our text in EditText
                        }
                    }

                    results.count = filteredList.size(); // set count of filtered list
                    results.values = filteredList; // set filtered list
                }
                return results; // return our filtered list
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFilter = (ArrayList<User>) results.values; // replace list to filtered list
                notifyDataSetChanged(); // refresh adapter
            }
        };

        return filter;
    }
}