package com.autohubtraining.autohub.scene.my_favourites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.favourite.Favourite;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFavourites extends BaseActivity implements FavouritePresenter.View {


    @BindView(R.id.rvMyFavourites)
    RecyclerView rvMyFavourites;

    @BindView(R.id.ivPic)
    ImageView ivPic;


    FavouriteAdapter adapter;

    MyFavouriteContract presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourites);
        ButterKnife.bind(this);


        setDataIntoViews();

        presenter = new MyFavouriteContract(this);
        presenter.getMyfaourites();


    }

    void setDataIntoViews() {
        UserData userData = DataHandler.getInstance().getCurrentUser();

        Glide.with(this).load(userData.getAvatarUrl()).into(ivPic);


    }

    @Override
    public void getMyFavourites(ArrayList<Favourite> alFavourite) {


        adapter = new FavouriteAdapter(this, alFavourite);
        rvMyFavourites.setAdapter(adapter);
        rvMyFavourites.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void hideLoading() {

        dismissLoading();
    }
}
