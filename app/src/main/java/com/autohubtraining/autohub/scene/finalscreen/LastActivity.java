package com.autohubtraining.autohub.scene.finalscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.scene.deshboard.DeshboardActivity;
import com.autohubtraining.autohub.util.AppConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.SCREEN9;
import static com.autohubtraining.autohub.util.AppConstants.SPLASH;

public class LastActivity extends BaseActivity implements LastContract.View {

    private LastPresenter presenter;

    @BindView(R.id.etPlanName)
    CustomEditView etPlanName;

    @BindView(R.id.etPlanName1)
    CustomEditView etPlanName1;


    @BindView(R.id.etPrice)
    CustomEditView etPrice;

    @BindView(R.id.etPrice1)
    CustomEditView etPrice1;

    @BindView(R.id.etNumberOfPics)
    CustomEditView etNumberOfPics;


    @BindView(R.id.toggle)
    ToggleButton toggle;

    @BindView(R.id.toggle_second)
    ToggleButton toggle_second;


    @BindView(R.id.svPlanType)
    Spinner svPlanType;

    @BindView(R.id.svPlanType1)
    Spinner svPlanType1;
    String selectedShootType = "", selectedShootType1 = "";

    ArrayList<String> alShootType = new ArrayList<>();
    ArrayList<UserPlan> alPlans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_last);
        ButterKnife.bind(this);

        setup();
    }


    private void setup() {
        presenter = new LastPresenter(this);
        alShootType.add("PHOTO SHOOT");
        alShootType.add("VIDEOGRAPHY");
        /* setting adapter in dropdown */
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alShootType);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        svPlanType.setAdapter(dataAdapter);
        svPlanType1.setAdapter(dataAdapter);
        svPlanType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedShootType = alShootType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        svPlanType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedShootType1 = alShootType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                setData();
                presenter.onNextBtnClicked(alPlans);
                break;
        }
    }

    void setData() {
        alPlans.clear();
        UserPlan plan = new UserPlan();
        plan.setPlanName(etPlanName.getText().toString());
        plan.setNumberOfPictures(etNumberOfPics.getText().toString());
        plan.setAmount(etPrice.getText().toString());
        plan.setEditingIncluded(toggle.isChecked() + "");
        plan.setShootType(selectedShootType);
        alPlans.add(plan);

        UserPlan plan1 = new UserPlan();
        plan1.setPlanName(etPlanName1.getText().toString());
        plan1.setAmount(etPrice1.getText().toString());
        plan1.setEditingIncluded(toggle_second.isChecked() + "");
        plan1.setShootType(selectedShootType1);
        alPlans.add(plan1);


    }

    @Override
    public void navigateToNextScreen() {
        DeshboardActivity.startActivity(LastActivity.this);

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
