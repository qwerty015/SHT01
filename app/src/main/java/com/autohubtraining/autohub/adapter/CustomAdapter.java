package com.autohubtraining.autohub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<String> items; //data source of the list adapter

    //public constructor
    public CustomAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_view_row_items, parent, false);
        }


        // get the TextView for item name and item description
        CustomEditView etAddFeatures = (CustomEditView)
                convertView.findViewById(R.id.etAddFeatures);


        //sets the text for item name and item description from the current item object
        etAddFeatures.setText(context.getText(R.string.mmlens));



        // returns the view for the current row
        return convertView;
    }
}