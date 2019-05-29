package com.autohubtraining.autohub.util;

import android.content.Context;
import android.widget.Toast;

public class Utill {
    public static void showToast(String msg, Context mContext) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }
}
