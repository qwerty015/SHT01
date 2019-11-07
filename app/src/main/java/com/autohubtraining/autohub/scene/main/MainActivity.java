package com.autohubtraining.autohub.scene.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.base.BaseActivity;
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

        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_explore);
        view_pager.setPagingEnabled(false);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        viewPagerAdapter.addFragment(new HomePhotographerFragment(), "title");
        viewPagerAdapter.addFragment(new ExploreFragment(), "title");

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
                logout();
//                if (viewPager != null)
//                    viewPager.setCurrentItem(2);

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
}
