package com.autohubtraining.autohub.scene.camerabrand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.model.public_data.CameraModel;

import java.util.ArrayList;

/**
 * Created by binod on 25/6/19.
 */

public class CustomModelItem extends BaseAdapter {
    Context context;
    ArrayList<CameraModel> alList = new ArrayList<>();
    LayoutInflater inflter;

    public CustomModelItem(Context applicationContext, ArrayList<CameraModel> alList) {
        this.context = applicationContext;
        this.alList = alList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return alList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_camera_brand, null);
        // ImageView icon = (ImageView) view.findViewById(R.id.tvBrandName);
        CustomTextView tvName = view.findViewById(R.id.tvBrandName);
        //icon.setImageResource(flags[i]);
        tvName.setText(alList.get(i).getModelName());
        return view;
    }
}
