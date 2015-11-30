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

/**
 * Created by icaboalo on 11/29/2015.
 */
public class KidInfoDialog extends DialogFragment {

    public static KidInfoDialog newInstance(){
        KidInfoDialog fragment = new KidInfoDialog();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = VUtil.inflateView(getActivity(), R.layout.dialog_kid_info);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        configureDialog(alertDialog, view);
        return alertDialog.create();
    }

    void configureDialog(AlertDialog.Builder alertDialog, View view){
        alertDialog.setView(view);
        alertDialog.setTitle(getString(R.string.dialog_add_team_title));
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(true);
    }
}
