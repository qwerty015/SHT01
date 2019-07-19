package com.autohubtraining.autohub.scene.base;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.autohubtraining.autohub.R;
import com.autohubtraining.autohub.scene.onboarding.OnBoardingActivity;
import com.autohubtraining.autohub.util.Loading;
import com.autohubtraining.autohub.util.Utill;
import com.google.firebase.auth.FirebaseAuth;

public class BaseFragment extends Fragment {
    private Loading loading;
    protected void showErrorToast(String errorMessage) {
        Utill.showToast(errorMessage, getActivity());
    }

    public void logout() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.logout))
                .setMessage(getResources().getString(R.string.logout_confirmation))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseAuth.getInstance().signOut();


                        Intent intent = new Intent(getActivity(), OnBoardingActivity.class);
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

}
