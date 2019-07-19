package com.autohubtraining.autohub.scene.camerabrand.EquipmentAdapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.autohubtraining.autohub.R;

import java.util.ArrayList;

/**
 * Created by binod on 25/6/19.
 */

public class EquipmentAdapter extends BaseAdapter {


    ArrayList<String> alEquipments = new ArrayList<>();
    boolean isMinusButtonHide = true;
    ItemClick itemClick;

    public EquipmentAdapter(Context context, ArrayList<String> alEquipments, boolean isHide) {
        this.alEquipments = alEquipments;

        this.isMinusButtonHide = isHide;


        itemClick = (ItemClick) context;

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
        return 0;
    }


    private static class ViewHolder {
        EditText etName;
        ImageView ivMinus;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_equipment, parent, false);
            viewHolder.etName = convertView.findViewById(R.id.etName);
            viewHolder.ivMinus = convertView.findViewById(R.id.ivMinus);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


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
                    if (position >= 0)
                        alEquipments.set(position, s.toString());
                }
                catch (Exception e)
                {

                }

            }
        });
        if (alEquipments.get(position) != null)
            viewHolder.etName.setText(alEquipments.get(position));

        if (isMinusButtonHide) {
            viewHolder.ivMinus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ivMinus.setVisibility(View.VISIBLE);
        }

        viewHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClick.removeItem(position);
//                alEquipments.remove(position);
//                notifyDataSetChanged();


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
