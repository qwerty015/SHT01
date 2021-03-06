package com.autohubtraining.autohub.scene.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.main.MainActivity;
import com.autohubtraining.autohub.util.AppUtils;
import com.autohubtraining.autohub.util.Loading;
import com.google.firebase.auth.FirebaseAuth;

public class BaseFragment extends Fragment {
    private Loading loading;

    protected void showErrorToast(String errorMessage) {
        AppUtils.showToast(errorMessage, getActivity());
    }

    public void logout() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.logout))
                .setMessage(getResources().getString(R.string.logout_confirmation))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().finish();

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

    protected void showLoading(String message) {
        if (loading == null) {
            loading = new Loading(getActivity());
        }
        if (!getActivity().isFinishing()) {
            loading.show(message);
        }
    }

    protected void dismissLoading() {
        if (loading != null && loading.isShowing() && !getActivity().isFinishing()) {
            loading.dismiss();
        }
    }

    /**
     * method is used for resizing of list-view.
     *
     * @param listView
     * @return
     */
    protected static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
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

}
