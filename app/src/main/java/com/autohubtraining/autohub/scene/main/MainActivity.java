package com.autohubtraining.autohub.scene.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.views.CustomViewPager;
import com.autohubtraining.autohub.util.views.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.view_pager)
    CustomViewPager view_pager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        User user = DataHandler.getInstance().getUser();

        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_explore);
        view_pager.setPagingEnabled(false);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        viewPagerAdapter.addFragment(user.getType() == AppConstants.CLIENT ? new HomeClientFragment() : new HomePhotographerFragment(), "title");
        viewPagerAdapter.addFragment(new ExploreFragment(), "title");
        viewPagerAdapter.addFragment(new BookingsFragment(), "title");

        view_pager.setAdapter(viewPagerAdapter);
        view_pager.setCurrentItem(1);
    }

    @OnClick({})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (view_pager != null)
                    view_pager.setCurrentItem(0);

                break;

            case R.id.navigation_explore:
                if (view_pager != null)
                    view_pager.setCurrentItem(1);

                break;

            case R.id.navigation_booking:
                if (view_pager != null)
                    view_pager.setCurrentItem(2);

                break;
        }

        return true;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void setViewPager(int pageIndex) {
        if (view_pager != null) {
            view_pager.setCurrentItem(pageIndex);

            if (pageIndex == 0) {
                navigation.setSelectedItemId(R.id.navigation_home);
            } else if (pageIndex == 1) {
                navigation.setSelectedItemId(R.id.navigation_explore);
            } else {
                navigation.setSelectedItemId(R.id.navigation_booking);
            }
        }
    }
}
