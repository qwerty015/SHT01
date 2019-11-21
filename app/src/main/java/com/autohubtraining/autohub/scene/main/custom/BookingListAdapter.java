package com.autohubtraining.autohub.scene.main.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.booking.Booking;
import com.autohubtraining.autohub.util.AppConstants;

import java.util.ArrayList;

public class BookingListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Booking> alBooking = new ArrayList<>();

    public BookingListAdapter(Context context, ArrayList<Booking> alBooking) {
        this.context = context;
        this.alBooking = alBooking;
    }

    @Override
    public int getCount() {
        return alBooking.size();
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
        TextView tv_bookingname;
        TextView tv_status_completed;
        TextView tv_status_cancelled;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        BookingListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new BookingListAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_booking, parent, false);

            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_bookingname = convertView.findViewById(R.id.tv_bookingname);
            viewHolder.tv_status_completed = convertView.findViewById(R.id.tv_status_completed);
            viewHolder.tv_status_cancelled = convertView.findViewById(R.id.tv_status_cancelled);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BookingListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        final Booking booking = alBooking.get(viewHolder.position);

        if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT) {
            viewHolder.tv_name.setText(booking.getPhotographerName());
        } else {
            viewHolder.tv_name.setText(booking.getClientName());
        }

        viewHolder.tv_bookingname.setText(booking.getName());

        if (booking.getStatus() == AppConstants.BOOKING_COMPLETED) {
            viewHolder.tv_status_completed.setVisibility(View.VISIBLE);
            viewHolder.tv_status_cancelled.setVisibility(View.GONE);
        } else if (booking.getStatus() == AppConstants.BOOKING_CANCELED) {
            viewHolder.tv_status_completed.setVisibility(View.GONE);
            viewHolder.tv_status_cancelled.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_status_completed.setVisibility(View.GONE);
            viewHolder.tv_status_cancelled.setVisibility(View.GONE);
        }

        return convertView;
    }

}
