package com.icaboalo.ecyd.ui.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.util.VUtil;

import butterknife.ButterKnife;

/**
 * Created by icaboalo on 11/28/2015.
 */
public class AddKidDialog extends DialogFragment {

    public static AddKidDialog newInstance(){
        AddKidDialog fragment = new AddKidDialog();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = VUtil.inflateView(getActivity(), R.layout.dialog_add_kid);
        ButterKnife.bind(this, view);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        configureDialog(alertDialog, view);
        return super.onCreateDialog(savedInstanceState);
    }

    private void configureDialog(AlertDialog.Builder alertDialog, View view) {
        alertDialog.setView(view);
        alertDialog.setTitle(getString(R.string.dialog_add_kid_title));
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
    }
}
