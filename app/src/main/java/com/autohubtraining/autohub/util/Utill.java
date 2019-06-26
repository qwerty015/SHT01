package com.autohubtraining.autohub.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Utill {
    public static void showToast(String msg, Context mContext) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }
    public static void openGallery(Context context, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.putExtra(SyncStateContract.Constants.E, 3); //set desired image limit here
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        return encoded;
    }

}
