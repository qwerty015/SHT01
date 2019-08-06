package com.autohubtraining.autohub.scene.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.data.DataHandler;
import com.autohubtraining.autohub.scene.onboarding.OnBoardingActivity;
import com.autohubtraining.autohub.util.Loading;
import com.autohubtraining.autohub.util.Utill;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;

import static com.autohubtraining.autohub.util.AppConstants.MAX_SCREEN_CLIENT;
import static com.autohubtraining.autohub.util.AppConstants.MAX_SCREEN_PHOTOGRAPHER;
import static com.autohubtraining.autohub.util.AppConstants.PHOTOGRAPHER;

public class BaseActivity  extends AppCompatActivity {
    private Loading loading;








    protected void showLoading(String message) {
        if (loading == null) {
            loading = new Loading(this);
        }
        if (!isFinishing() && loading!=null) {
            loading.show(message);
        }
    }

    protected void dismissLoading() {
        if (loading != null && loading.isShowing() && !this.isFinishing()) {
            loading.dismiss();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    protected void showErrorToast(String errorMessage) {
        Utill.showToast(errorMessage, this);
    }

    public void logout() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.logout))
                .setMessage(getResources().getString(R.string.logout_confirmation))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
