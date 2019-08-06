package com.autohubtraining.autohub.scene.explore;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.model.user.UserData;

import java.util.ArrayList;
import java.util.List;

public class ExploreCarouselPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    private FragmentActivity context;
    private FragmentManager fragmentManager;
    private float scale = 1.0f;
    int pagerID;
    List<UserData> alUsers;
    Fragment fragment;

    public ExploreCarouselPagerAdapter(FragmentActivity context, FragmentManager fm, int id, List<UserData> alUsers, Fragment fragment) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
        pagerID = id;


        this.alUsers = new ArrayList<>();
        this.alUsers = alUsers;

        this.fragment = fragment;

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);

        return position;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others
        try {


            if (position == 0)
                scale = BIG_SCALE;
            else
                scale = SMALL_SCALE;

            position = position % alUsers.size();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ItemFragment.newInstance(context, position, scale, alUsers.get(position), fragment);
    }

    @Override
    public int getCount() {


        return alUsers.size();
    }

    void updateData(UserData data, int pos) {

        alUsers.set(pos, data);
        if (context != null)
            notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                CarouselLinearLayout cur = getRootView(position);
                cur.setScaleBoth(BIG_SCALE - DIFF_SCALE * positionOffset);

                CarouselLinearLayout next = getRootView(position + 1);
                next.setScaleBoth(SMALL_SCALE + DIFF_SCALE * positionOffset);

            }

        } catch (Exception e) {

            Log.e("expectionnn", alUsers.size() + " sissss" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private CarouselLinearLayout getRootView(int position) {


        if (fragmentManager.findFragmentByTag(this.getFragmentTag(position)).getView() != null)
            return fragmentManager.findFragmentByTag(this.getFragmentTag(position))
                    .getView().findViewById(R.id.root_container);
        else
            return null;

    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + pagerID + ":" + position;
    }
}
