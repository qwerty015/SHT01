package com.autohubtraining.autohub.scene.booking;

import android.os.Bundle;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GiveReviewActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_give_review);
        ButterKnife.bind(this);

    }

    @OnClick({})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {

        }
    }
}
