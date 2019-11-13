package com.autohubtraining.autohub.scene.explore;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.events.UserFavorites;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ExploreFragment extends BaseFragment implements ExploreContract.View, ItemFragment.ItemClick, ExploreItem.ItemClick {

    public ExploreCarouselPagerAdapter adapter;
    public ViewPager pager;
    ExplorePresenter explorePresenter;
    List<UserData> alUsers = new ArrayList<>();
    View retView = null;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.picker)
    DiscreteScrollView picker;
    ExploreItem exploreAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (retView == null) {

            retView = inflater.inflate(R.layout.activity_explore, container, false);
            pager = (ViewPager) retView.findViewById(R.id.myviewpager);
            ButterKnife.bind(this, retView);
            initData();

            pager.addOnPageChangeListener(adapter);

            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() > 0) {
                        // explorePresenter.filterData(s.toString());
                    }

                }
            });

        }

        return retView;
    }

    void initData() {
        explorePresenter = new ExplorePresenter(this);
        explorePresenter.loadData();
    }

    @Override
    public void getUsers(List<UserData> alUsers) {

        this.alUsers = alUsers;

        if (alUsers.size() > 0) {
            DisplayMetrics metrics = new DisplayMetrics();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() != null) {
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

                        int pageMargin = ((int)(metrics.widthPixels / 6.5));
                        Log.e("pagemargein",""+pageMargin);
                        pager.setClipToPadding(false);
                        pager.setClipChildren(false);
                        pager.setPadding(pageMargin,0,pageMargin,0);
                        //pager.setPageMargin(2);
                        pager.setOffscreenPageLimit(3);
                        adapter = new ExploreCarouselPagerAdapter(getActivity(), getActivity().getSupportFragmentManager(), pager.getId(), alUsers, ExploreFragment.this);
                        pager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        pager.addOnPageChangeListener(adapter);



                        //code for recycler view adapter
                        /*picker.setOrientation(DSVOrientation.HORIZONTAL);

                        picker.setItemTransformer(new ScaleTransformer.Builder()
                                .setMinScale(0.8f)
                                .build());

                        picker.setOffscreenItems(3);

                        exploreAdapter = new ExploreItem(getActivity(), alUsers, (metrics.widthPixels - pageMargin), ExploreFragment.this);
                        picker.setAdapter(exploreAdapter);*/


                    }
                }
            }, 500);
        }


    }

    @Override
    public void navigateToNextScreen() {

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

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void getAction(int action) {

    }

    @Override
    public void setFavourite(boolean isFavourite, UserData userData) {

        explorePresenter.setFavourite(userData, isFavourite);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    /* recieving the broadcast message when user set the favourite from view more screen  */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(UserFavorites userFavorites) {
        if (userFavorites != null) {

            // int pos = picker.getCurrentItem();
            int pos = pager.getCurrentItem();


            Log.e("pos", pos + "");

            UserData userData = alUsers.get(pos);
            userData.setFavourite(Boolean.parseBoolean(userFavorites.getIsFavorite()));


            alUsers.set(pos, userData);
            //for recycler view
            /*if (exploreAdapter != null)
                exploreAdapter.notifyDataSetChanged();*/



            if (adapter != null) {
                //adapter.notifyDataSetChanged();
                //adapter.getItem(pos);
                adapter.updateData(userData,pos);

                //pager.setAdapter(adapter);
                //pager.invalidate();

            }


            EventBus.getDefault().removeStickyEvent(userFavorites);
        }

    }
}
