package com.autohubtraining.autohub.scene.viewmore1;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.editprofile.MyViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewMore1Activity extends AppCompatActivity implements ViewMore1Contract.View {
    @BindView(R.id.tvReadMore)
    TextView tvReadMore;
    @BindView(R.id.llProductsDescription)
    LinearLayout llProductsDescription;
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    MyViewPagerAdapter adapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tabs)
    TabLayout tabLayout1;

    private ViewMore1Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more1);
        ButterKnife.bind(this);
        adapter = new MyViewPagerAdapter(ViewMore1Activity.this, new ArrayList<>());
        vpPager.setAdapter(adapter);
        presenter = new ViewMore1Presenter(this);
        presenter.onCreate();

    }

    @OnClick({R.id.tvReadMore})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvReadMore:
                if (llProductsDescription.getVisibility() == View.VISIBLE) {
                    llProductsDescription.setVisibility(View.GONE);
                    tvReadMore.setVisibility(View.VISIBLE);
                } else {
                    llProductsDescription.setVisibility(View.VISIBLE);
                    tvReadMore.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void navigateToNextScreen() {

    }

    @Override
    public void addTabLayout() {
        tabLayout.setupWithViewPager(vpPager, true);
        tabLayout1.addTab(tabLayout1.newTab().setText("RS. 120"));
        tabLayout1.addTab(tabLayout1.newTab().setText("RS. 200"));
        tabLayout1.addTab(tabLayout1.newTab().setText("OFFER"));
    }
}
