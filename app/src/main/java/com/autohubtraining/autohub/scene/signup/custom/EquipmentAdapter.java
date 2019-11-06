package com.autohubtraining.autohub.scene.signup.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.autohubtraining.autohub.R;

import java.util.ArrayList;

public class EquipmentAdapter extends BaseAdapter {
    ArrayList<String> alEquipments = new ArrayList<>();
    boolean isMinusButtonHide = true;
    EquipmentAdapter.ItemClick itemClick;

    public EquipmentAdapter(Context context, ArrayList<String> alEquipments, boolean isHidden) {
        this.alEquipments = alEquipments;
        this.isMinusButtonHide = isHidden;

        itemClick = (EquipmentAdapter.ItemClick) context;
    }

    @Override
    public int getCount() {
        return alEquipments.size();
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
        EditText etName;
        ImageView ivMinus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        EquipmentAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new EquipmentAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_equipment, parent, false);

            viewHolder.etName = convertView.findViewById(R.id.etName);
            viewHolder.ivMinus = convertView.findViewById(R.id.ivMinus);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EquipmentAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.position = position;

        viewHolder.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (viewHolder.position >= 0) {
                        alEquipments.set(viewHolder.position, s.toString());
                    }
                }
                catch (Exception e)
                {

                }

            }
        });

//        if (alEquipments.get(position) != null)
//            viewHolder.etName.setText(alEquipments.get(position));

        if (isMinusButtonHide) {
            viewHolder.ivMinus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ivMinus.setVisibility(View.VISIBLE);
        }

        viewHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.removeItem(position);
            }
        });

        return result;
    }

    public ArrayList<String> getEquipment() {
        return alEquipments;
    }

    public interface ItemClick {
        void removeItem(int pos);
    }
}
