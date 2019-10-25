package com.autohubtraining.autohub.scene.choose;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.name.NameActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends AppCompatActivity implements ChooseContract.View {

    private ChooseContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup_choose);
        ButterKnife.bind(this);

        setup();
    }

    private void setup() {
        presenter = new ChoosePresenter(this);

    }

    @Override
    public void navigateToSignUpName() {
        Intent intent = new Intent(this, NameActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.client, R.id.photographer})
    void onClickItems(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.client:
                presenter.onClientBtnClicked();
                break;
            case R.id.photographer:
                presenter.onPhotographerBtnClicked();
                break;
        }
    }
}
