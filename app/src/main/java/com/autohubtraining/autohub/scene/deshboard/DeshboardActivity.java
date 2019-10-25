package com.autohubtraining.autohub.scene.deshboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.home_screen_photographers.HomeScreenPhotographers;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.views.CustomViewPager;
import com.autohubtraining.autohub.util.views.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.deshboard.userProfile.UserProfileFragment;
import com.autohubtraining.autohub.scene.explore.ExploreFragment;
import com.autohubtraining.autohub.scene.homescreen.HomeFragment;

import butterknife.ButterKnife;

public class DeshboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager1);
        viewPager.setPagingEnabled(false);

        UserData userData = DataHandler.getInstance().getCurrentUser();

        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        adapter.addFragment(userData.getType() == AppConstants.PHOTOGRAPHER ? new HomeScreenPhotographers() : new HomeFragment(), "title");
        adapter.addFragment(new ExploreFragment(), "title");
        adapter.addFragment(new UserProfileFragment(), "title");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);


    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DeshboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (viewPager != null)
                    viewPager.setCurrentItem(0);
                //fragment = new HomeFragment();
                break;

            case R.id.navigation_preview:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ExploreFragment()).commit();
                //fragment = ExploreFragment.newInstance(this);
                if (viewPager != null)
                    viewPager.setCurrentItem(1);

                break;

            case R.id.navigation_bookings:
                if (viewPager != null)
                    viewPager.setCurrentItem(2);
                //fragment = new UserProfileFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
            return true;
        }
        return false;
    }
}