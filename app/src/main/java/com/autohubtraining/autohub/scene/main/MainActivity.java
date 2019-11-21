package com.autohubtraining.autohub.scene.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.events.CustomEvent;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.views.CustomViewPager;
import com.autohubtraining.autohub.util.views.ViewPagerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.view_pager)
    CustomViewPager view_pager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    ViewPagerAdapter viewPagerAdapter;

    int PERMISSION_ID = 42;

    HomePhotographerFragment homePhotographerFragment;
    HomeClientFragment homeClientFragment;
    ExploreFragment exploreFragment;
    BookingsFragment bookingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        User user = DataHandler.getInstance().getUser();

        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_explore);
        view_pager.setPagingEnabled(false);

        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        if (user.getType() == AppConstants.CLIENT) {
            homeClientFragment = new HomeClientFragment();
        } else {
            homePhotographerFragment = new HomePhotographerFragment();
        }

        exploreFragment = new ExploreFragment();
        bookingsFragment = new BookingsFragment();

        viewPagerAdapter.addFragment(user.getType() == AppConstants.CLIENT ? homeClientFragment : homePhotographerFragment, "title");
        viewPagerAdapter.addFragment(exploreFragment, "title");
        viewPagerAdapter.addFragment(bookingsFragment, "title");

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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showLoading("");
                Location loc = getLocation();
                dismissLoading();

                saveUserLocation(new GeoPoint(loc.getLatitude(), loc.getLongitude()));
            }
        }
    }

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        } else {
            showLoading("");
            Location loc = getLocation();
            dismissLoading();

            if (loc == null) {
                exploreFragment.getPhotographers();
            } else {
                saveUserLocation(new GeoPoint(loc.getLatitude(), loc.getLongitude()));
            }
        }
    }

    /**
     * method is used for getting location of device.
     *
     * @param
     * @return
     */
    private Location getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return lastKnownLocationGPS;
            } else {
                Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                return loc;
            }
        } else {
            return null;
        }
    }

    /**
     * method is used for saving user location to Firestore.
     *
     * @param location
     * @return
     */
    private void saveUserLocation(GeoPoint location) {
        User user = DataHandler.getInstance().getUser();

        Map<String, Object> data = new HashMap<>();
        data.put("location", location);

        showLoading("");

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                user.setLocation(location);
                exploreFragment.setLocation();
                exploreFragment.getPhotographers();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                exploreFragment.getPhotographers();

                showErrorToast(e.getMessage());
                Log.e(AppConstants.TAG, "data failed with an exception" + e.toString());
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCustomEvent(CustomEvent event) {
        if (event.data == "Updated Profile") {
            if (DataHandler.getInstance().getUser().getType() == AppConstants.CLIENT) {
                homeClientFragment.updateAvatar();
            } else {
                homePhotographerFragment.updateAvatar();
            }

            exploreFragment.updateAvatar();
            bookingsFragment.updateAvatar();
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
                if (view_pager != null) {
                    view_pager.setCurrentItem(2);
                }

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
