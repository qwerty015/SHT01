package com.autohubtraining.autohub.scene.signup.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.model.public_data.CameraBrand;

import java.util.ArrayList;

public class CameraBrandAdapter extends BaseAdapter {
    Context context;
    ArrayList<CameraBrand> alList = new ArrayList<>();
    LayoutInflater inflater;

    public CameraBrandAdapter(Context applicationContext, ArrayList<CameraBrand> alList) {
        this.context = applicationContext;
        this.alList = alList;
        inflater = (LayoutInflater.from(applicationContext));
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
        view = inflater.inflate(R.layout.item_camera_brand, null);

        CustomTextView tvName = view.findViewById(R.id.tvBrandName);
        tvName.setText(alList.get(i).getBrandName());

        return view;
    }
}
