package com.autohubtraining.autohub.scene.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user_plan.UserPlan;
import com.autohubtraining.autohub.scene.base.BaseActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_managepackages);
        ButterKnife.bind(this);

        showPackagesToView();
    }

    @OnClick({})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {

        }
    }

    /**
     * method is used for showing packages to screen.
     *
     * @param
     * @return
     */
    private void showPackagesToView() {
        User user = DataHandler.getInstance().getUser();
        ArrayList<UserPlan> alPlans = user.getArrayPlan();

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

}
