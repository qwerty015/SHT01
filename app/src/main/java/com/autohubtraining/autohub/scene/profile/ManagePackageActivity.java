package com.autohubtraining.autohub.scene.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ManagePackageActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_package_name1)
    TextView tv_package_name1;
    @BindView(R.id.tv_package_name2)
    TextView tv_package_name2;
    @BindView(R.id.et_numphotographs1)
    EditText et_numphotographs1;
    @BindView(R.id.et_numphotographs2)
    EditText et_numphotographs2;
    @BindView(R.id.tv_editingincluded1)
    TextView tv_editingincluded1;
    @BindView(R.id.tv_editingincluded2)
    TextView tv_editingincluded2;
    @BindView(R.id.et_rate1)
    EditText et_rate1;
    @BindView(R.id.et_rate2)
    EditText et_rate2;
    @BindView(R.id.iv_edit1)
    ImageView iv_edit1;
    @BindView(R.id.iv_edit2)
    ImageView iv_edit2;

    ArrayList<UserPlan> alPlans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_managepackages);
        ButterKnife.bind(this);

        showPackagesToView();
    }

    @OnClick({R.id.iv_edit1, R.id.iv_edit2})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_edit1:
                et_numphotographs1.setEnabled(true);
                et_rate1.setEnabled(true);
                iv_edit1.setVisibility(View.GONE);
                break;
            case R.id.iv_edit2:
                et_numphotographs2.setEnabled(true);
                et_rate2.setEnabled(true);
                iv_edit2.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        savePackagesToStore();
    }

    /**
     * method is used for showing packages to screen.
     *
     * @param
     * @return
     */
    private void showPackagesToView() {
        User user = DataHandler.getInstance().getUser();
        alPlans = user.getArrayPlan();

        Glide.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.ic_profile).into(iv_avatar);

        if (alPlans != null) {
            tv_package_name1.setText(alPlans.get(0).getPlanName());
            et_numphotographs1.setText(alPlans.get(0).getNumberOfPictures());
            et_rate1.setText(alPlans.get(0).getPrice());

            tv_package_name2.setText(alPlans.get(1).getPlanName());
            et_numphotographs2.setText(alPlans.get(1).getNumberOfPictures());
            et_rate2.setText(alPlans.get(1).getPrice());

            if (alPlans.get(0).getEditingIncluded().equals("true")) {
                tv_editingincluded1.setText("YES");
            } else {
                tv_editingincluded1.setText("NO");
            }

            if (alPlans.get(1).getEditingIncluded().equals("true")) {
                tv_editingincluded2.setText("YES");
            } else {
                tv_editingincluded2.setText("NO");
            }

            et_numphotographs1.setEnabled(false);
            et_numphotographs2.setEnabled(false);
            et_rate1.setEnabled(false);
            et_rate2.setEnabled(false);
        }
    }

    /**
     * method is used for saving package data to store.
     *
     * @param
     * @return
     */
    private void savePackagesToStore() {
        if (iv_edit1.getVisibility() == View.GONE || iv_edit2.getVisibility() == View.GONE) {
            alPlans.get(0).setNumberOfPictures(et_numphotographs1.getText().toString());
            alPlans.get(0).setPrice(et_rate1.getText().toString());

            alPlans.get(1).setNumberOfPictures(et_numphotographs2.getText().toString());
            alPlans.get(1).setPrice(et_rate2.getText().toString());

            User user = DataHandler.getInstance().getUser();

            Map<String, Object> data = new HashMap<>();
            data.put("arrayPlan", alPlans);

            showLoading("");

            /* set data into firebase database*/
            FirebaseFirestore.getInstance().collection(AppConstants.ref_user).document(user.getUserId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dismissLoading();

                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissLoading();

                    finish();
                    showErrorToast(e.getMessage());
                    Log.e(AppConstants.TAG, "data failed with an exception" + e.toString());
                }
            });
        } else {
            finish();
        }
    }
}
