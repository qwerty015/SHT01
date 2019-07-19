package com.autohubtraining.autohub.scene.my_favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.model.favourite.Favourite;
import com.autohubtraining.autohub.data.model.user.UserData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    ArrayList<Favourite> alFavourite = new ArrayList<>();


    // RecyclerView recyclerView;
    public FavouriteAdapter(Context context, ArrayList<Favourite> alFavourite) {

        this.context = context;
        this.alFavourite = alFavourite;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_favourite, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        ViewHolder myholder = (ViewHolder) holder;
        Favourite favourite = alFavourite.get(position);
        myholder.tvName.setText(favourite.getName());
    }


    @Override
    public int getItemCount() {
        return alFavourite.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;


        public ViewHolder(View linearLayout) {
            super(linearLayout);

            ButterKnife.bind(this, linearLayout);


        }
    }

    interface ItemClick {
        void getAction(int action);

    }
}

