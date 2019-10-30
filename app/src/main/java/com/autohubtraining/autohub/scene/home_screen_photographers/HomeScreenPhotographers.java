package com.autohubtraining.autohub.scene.home_screen_photographers;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomTextView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.settings.SettingsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenPhotographers extends BaseFragment {


    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvPrice)
    CustomTextView tvPrice;

    @BindView(R.id.tvCamera)
    TextView tvCamera;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_home_screen_photographer, container, false);
        ButterKnife.bind(this, retView);
        setDataIntoViews();

        retView.findViewById(R.id.ivEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);


            }
        });
        return retView;
    }

    void setDataIntoViews() {


        UserData user = DataHandler.getInstance().getCurrentUser();
        if (user != null && user.getAvatarUrl() != null)
            Glide.with(getActivity()).load(user.getAvatarUrl()).transform(new CircleCrop()).placeholder(R.mipmap.profile_image_icon).into(ivPic);

        Spannable wordtoSpan = new SpannableString(tvPrice.getText().toString());
        ColorStateList greenColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{0xff60E01A});
        TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.NORMAL, 50, greenColor, null);
        wordtoSpan.setSpan(highlightSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (user.getUserCamera() != null && user.getUserCamera().getCameraBrand() != null && user.getUserCamera().getCameraModel() != null) {
            tvCamera.setText(user.getUserCamera().getCameraBrand().toUpperCase() + " " + user.getUserCamera().getCameraModel());
        }

        wordtoSpan.setSpan(new TextAppearanceSpan(null, Typeface.BOLD, 80, greenColor, null), 3, tvPrice.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(wordtoSpan);

    }

}
