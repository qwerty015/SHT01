package com.autohubtraining.autohub.scene.deshboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.BaseActivity;

public class DeshboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, DeshboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        ((BaseActivity)context).finishAffinity();
        context.startActivity(intent);
    }
}
