package com.autohubtraining.autohub.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.location.Location;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.autohubtraining.autohub.R;
import com.google.firebase.firestore.GeoPoint;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static void showToast(String msg, Context mContext) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }

    public static float getDistance(GeoPoint point1, GeoPoint point2) {
        if (point1 == null || point2 == null) {
            return 0;
        }

        Location loc1 = new Location("");
        loc1.setLatitude(point1.getLatitude());
        loc1.setLongitude(point1.getLongitude());

        Location loc2 = new Location("");
        loc2.setLatitude(point2.getLatitude());
        loc2.setLongitude(point2.getLongitude());

        return loc1.distanceTo(loc2) / 1000.0f;
    }

    /**
     * method is used for checking valid email address format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * method is used for hiding keyboard from activity.
     *
     * @param activity
     * @return boolean true for valid false for invalid
     */
    public static void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * method is used for showing keyboard in activity.
     *
     * @param activity
     * @return boolean true for valid false for invalid
     */
    public static void showKeyboard(Activity activity, EditText editText) {
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
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

    public static Bitmap addBorder(Bitmap resource, Context context) {
        int w = resource.getWidth();
        int h = resource.getHeight();
        int radius = Math.min(h / 2, w / 2);
        Bitmap output = Bitmap.createBitmap(w + 8, h + 8, Bitmap.Config.ARGB_8888);
        Paint p = new Paint();
        p.setAntiAlias(true);
        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);
        c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        c.drawBitmap(resource, 4, 4, p);
        p.setXfermode(null);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        p.setStrokeWidth(3);
        c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);
        return output;
    }

}
