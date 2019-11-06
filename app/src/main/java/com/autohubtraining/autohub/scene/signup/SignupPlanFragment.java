package com.autohubtraining.autohub.scene.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseFragment;
import com.autohubtraining.autohub.scene.main.MainActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupPlanFragment extends BaseFragment {

    @BindView(R.id.etPlanName)
    CustomEditView etPlanName;

    @BindView(R.id.etPlanName1)
    CustomEditView etPlanName1;

    @BindView(R.id.etNumberOfPics)
    CustomEditView etNumberOfPics;

    @BindView(R.id.etNumberOfPics1)
    CustomEditView etNumberOfPics1;

    @BindView(R.id.toggle_first)
    ToggleButton toggle_first;

    @BindView(R.id.toggle_second)
    ToggleButton toggle_second;

    @BindView(R.id.etPrice)
    CustomEditView etPrice;

    @BindView(R.id.etPrice1)
    CustomEditView etPrice1;

    @BindView(R.id.svPlanType)
    Spinner svPlanType;

    @BindView(R.id.svPlanType1)
    Spinner svPlanType1;

    String selectedShootType = "", selectedShootType1 = "";

    ArrayList<String> alShootType = new ArrayList<>();
    ArrayList<UserPlan> alPlans = new ArrayList<>();

    SignupActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_signup_plan, container, false);
        ButterKnife.bind(this, retView);

        activity = (SignupActivity) getActivity();

        etPlanName.setText("plan1");
        etNumberOfPics.setText("3");
        etPrice.setText("3.5");
        etPlanName1.setText("plan2");
        etNumberOfPics1.setText("4");
        etPrice1.setText("4.5");

        alShootType.add("PHOTO SHOOT");
        alShootType.add("VIDEOGRAPHY");

        /* setting adapter in dropdown */
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, alShootType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        svPlanType.setAdapter(dataAdapter);
        svPlanType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedShootType = alShootType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        svPlanType1.setAdapter(dataAdapter);
        svPlanType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedShootType1 = alShootType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return retView;
    }

    @OnClick({R.id.nextBtn})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                setPlanData();
                break;
        }
    }

    /**
     * method is used for setting plan data.
     *
     * @param
     * @return
     */
    void setPlanData() {
        alPlans.clear();

        UserPlan plan = new UserPlan();
        plan.setPlanName(etPlanName.getText().toString());
        plan.setShootType(selectedShootType);
        plan.setNumberOfPictures(etNumberOfPics.getText().toString());
        plan.setEditingIncluded(toggle_first.isChecked() + "");
        plan.setPrice(etPrice.getText().toString());
        alPlans.add(plan);

        UserPlan plan1 = new UserPlan();
        plan1.setPlanName(etPlanName1.getText().toString());
        plan1.setShootType(selectedShootType1);
        plan1.setNumberOfPictures(etNumberOfPics1.getText().toString());
        plan1.setEditingIncluded(toggle_second.isChecked() + "");
        plan1.setPrice(etPrice1.getText().toString());
        alPlans.add(plan1);

        saveUserDataToFireStore();
    }

    /**
     * method is used for saving user data to firestore.
     *
     * @param
     * @return
     */
    private void saveUserDataToFireStore() {
        showLoading("");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = DataHandler.getInstance().getUser();
        user.setArrayPlan(alPlans);

        /* set data into firebase database*/
        FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dismissLoading();

                MainActivity.startActivity(activity);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                showErrorToast(e.toString());
                Log.e("firestore", "data failed with an exception" + e.toString());
            }
        });
    }
}
