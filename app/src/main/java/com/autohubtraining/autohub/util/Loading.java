package com.autohubtraining.autohub.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.autohubtraining.autohub.R;


public class Loading {

    private Context context;
    private Dialog dialog;

    public Loading(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void show(String message) {
        try {
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_loading, null);
            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);
            dialog.setContentView(view);
            dialog.getWindow().setLayout(-1, -2);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing() && !((Activity) context).isDestroyed()) {
            dialog.dismiss();
        }
    }

    public void showSolidProgress(String message) {
        dialog = new Dialog(context);
        try {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(R.color.colorWhite);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_custom_loading, null);
            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);
            dialog.setContentView(view);
            dialog.getWindow().setLayout(-1, -2);
            dialog.setCancelable(false);
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


