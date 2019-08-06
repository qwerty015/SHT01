package com.autohubtraining.autohub.scene.editdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.customview.CustomButton;
import com.autohubtraining.autohub.customview.CustomEditView;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.BaseActivity;
import com.autohubtraining.autohub.util.Utill;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDetailsActivity extends BaseActivity implements EditDetailContract.View, OnOtpCompletionListener {


    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;

    @BindView(R.id.etLastName)
    EditText etLastName;


    @BindView(R.id.countryCodePicker)
    CountryCodePicker countryCodePicker;

    EditDetailsPresenter editDetailsPresenter;


    @BindView(R.id.btnUpdate)
    CustomButton btnUpdate;


    @BindView(R.id.otp_view)
    OtpView otpView;
    UserData userData;

    Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        ButterKnife.bind(this);
        editDetailsPresenter = new EditDetailsPresenter(this);

        // String[] recourseList = this.getResources().getStringArray(R.array.CountryCodes);
        setDataIntoViews();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e("counrrtyCode", countryCodePicker.getFullNumberWithPlus());
//                if (editDetailsPresenter.isNumberValid(countryCodePicker)) {
//
//                    editDetailsPresenter.sendPhone(countryCodePicker.getFullNumberWithPlus());
//                } else {
//                    Utill.showToast(getString(R.string.invalid_no), EditDetailsActivity.this);
//
//                }

                map.put("firstName", etName.getText().toString());

                map.put("lastName", etLastName.getText().toString());
                map.put("countryCode", countryCodePicker.getSelectedCountryCodeWithPlus());


                String phoneText = etPhoneNumber.getText().toString().replaceAll("\\s+", "");
                //Log.e("phoneText", phoneText.toString() + "  " + userData.getPhoneNo());

                if (!(phoneText.equals(userData.getPhoneNo()))) {


                    if (editDetailsPresenter.isNumberValid(countryCodePicker)) {
                        map.put("phoneNo", etPhoneNumber.getText().toString().replaceAll(" ", ""));
                        editDetailsPresenter.sendPhone(countryCodePicker.getFullNumberWithPlus());
                    } else {
                        Utill.showToast(getString(R.string.invalid_no), EditDetailsActivity.this);

                    }

                } else {
                    editDetailsPresenter.updateData(map);
                }


            }
        });
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


//                if (userData != null) {
//
//                    if (!(s.toString().equals(userData.getPhoneNo())) && s.toString().length() >= 10) {
//
//                        if (editDetailsPresenter.isNumberValid(countryCodePicker)) {
//                            editDetailsPresenter.sendPhone(countryCodePicker.getFullNumberWithPlus());
//                        } else {
//                            Utill.showToast(getString(R.string.invalid_no), EditDetailsActivity.this);
//
//                        }
//
//                    }
//
//
//                }


            }
        });


    }


    void setDataIntoViews() {
        otpView.setOtpCompletionListener(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        countryCodePicker.setTypeFace(typeFace);
        countryCodePicker.setDialogTypeFace(typeFace);


        userData = DataHandler.getInstance().getCurrentUser();
        etName.setText(userData.getFirstName());
        etLastName.setText(userData.getLastName().toString());


        etPhoneNumber.setText(userData.getPhoneNo());


        countryCodePicker.registerCarrierNumberEditText(etPhoneNumber);
        otpView.setTypeface(typeFace);


    }

    @Override
    public void navigateToNextScreen() {


        Log.e("wokingg", "workingg");
        EventBus.getDefault().postSticky("Updated");
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void verifyOTP() {
        otpView.setVisibility(View.VISIBLE);
    }

    @Override
    public void OTPVerifiedSuccessFully() {

        editDetailsPresenter.updateData(map);

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
    public void onOtpCompleted(final String otp) {

        map.put("firstName", etName.getText().toString());
        map.put("phoneNo", etPhoneNumber.getText().toString());
        map.put("lastName", etLastName.getText().toString());
        map.put("countryCode", countryCodePicker.getSelectedCountryCodeWithPlus());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editDetailsPresenter.verifyOtp(otp, map);
            }
        }, 200);
    }


}
