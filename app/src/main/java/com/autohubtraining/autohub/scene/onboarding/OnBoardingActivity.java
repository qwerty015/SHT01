package com.autohubtraining.autohub.scene.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.public_data.CameraBrand;
import com.autohubtraining.autohub.data.model.public_data.CameraModel;
import com.autohubtraining.autohub.data.model.user.UserData;
import com.autohubtraining.autohub.scene.SignupBaseActivity;
import com.autohubtraining.autohub.scene.camerainfo.CameraInfoActivity;
import com.autohubtraining.autohub.scene.choose.ChooseActivity;
import com.autohubtraining.autohub.scene.deshboard.DeshboardActivity;
import com.autohubtraining.autohub.scene.login.LoginActivity;
import com.autohubtraining.autohub.scene.name.NameActivity;
import com.autohubtraining.autohub.util.AppConstants;
import com.autohubtraining.autohub.util.Loading;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.autohubtraining.autohub.util.AppConstants.CLIENT;
import static com.autohubtraining.autohub.util.AppConstants.SPLASH;


public class OnBoardingActivity extends AppCompatActivity implements OnBoardingContract.View {

    private OnBoardingContract.Presenter presenter;
    private Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        //setProgressBar(SPLASH);

        setup();

        //getBrandData();
        //getModels();


    }

    private void setup() {
        presenter = new OnBoardingPresenter(this);
        presenter.isLogin();
//        try {
//            presenter.postData();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void navigateToSignUp() {
        startActivity(new Intent(this, ChooseActivity.class));
    }

    @Override
    public void navigateToHomeScreen() {


        UserData user = DataHandler.getInstance().getCurrentUser();
        if (user.getType() == CLIENT) {
            DeshboardActivity.startActivity(this);
        } else {
            DeshboardActivity.startActivity(this);
        }

    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick({R.id.login, R.id.signup})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login:
                presenter.onLoginBtnClicked();
                break;
            case R.id.signup:
                presenter.onSignUpBtnClicked();
                break;
        }
    }


    void getBrandData() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //DocumentReference cities = db.collection("dummyyyy").document("camera_brands");
        db.collection(AppConstants.public_data).document("camera_brands").addSnapshotListener((documentSnapshot, e) -> {


            Map<String, Object> map = documentSnapshot.getData();

            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            //please check
            ArrayList<CameraBrand> alBrands = new ArrayList<>();

            while (itr.hasNext()) {
                HashMap<String, String> brand = (HashMap<String, String>) itr.next().getValue();
                CameraBrand cameraBrand = new CameraBrand();
                cameraBrand.setBrandName(brand.get("brandName"));
                cameraBrand.setBrandId(brand.get("brandId"));
                alBrands.add(cameraBrand);

            }


        });


    }

    void getModels() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //DocumentReference cities = db.collection("dummyyyy").document("camera_brands");
        db.collection(AppConstants.public_data).document("camera_models").addSnapshotListener((documentSnapshot, e) -> {

            Map<String, Object> map = documentSnapshot.getData();
            map = (Map<String, Object>) map.get("YVysn6OgVXWrEfJffzFY");
            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            ArrayList<CameraModel> alBrands = new ArrayList<>();
            while (itr.hasNext()) {
                HashMap<String, String> brand = (HashMap<String, String>) itr.next().getValue();
                CameraModel cameraBrand = new CameraModel();
                cameraBrand.setModelId(brand.get("modelId"));
                cameraBrand.setModelName(brand.get("modelName"));
                Log.e("brandddd", itr.next().getValue().toString());
                alBrands.add(cameraBrand);

            }


        });


    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading() {

        showLoading("Loading User Data");
    }

    @Override
    public void hideLoading() {
        dismissLoading();

    }

    protected void showLoading(String message) {
        if (loading == null) {
            loading = new Loading(this);
        }
        if (!isFinishing()) {
            loading.show(message);
        }
    }

    protected void dismissLoading() {
        if (loading != null && loading.isShowing() && !this.isFinishing()) {
            loading.dismiss();
        }
    }
}
