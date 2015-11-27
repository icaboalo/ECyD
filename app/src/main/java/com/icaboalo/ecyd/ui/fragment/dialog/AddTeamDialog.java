package com.icaboalo.ecyd.ui.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.ParseModel;
import com.icaboalo.ecyd.util.VUtil;
import com.parse.ParseObject;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 11/27/2015.
 */
public class AddTeamDialog extends DialogFragment {

    @Bind(R.id.team_name_input)
    EditText mTeamNameInput;

    public static AddTeamDialog newInstance(String title){
        AddTeamDialog fragment = new AddTeamDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = VUtil.inflateView(getActivity(), R.layout.dialog_add_team);
        ButterKnife.bind(this, view);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        configureDialog(alertDialog, view);
        return alertDialog.create();
    }

    void configureDialog(AlertDialog.Builder alertDialog, View view){
        alertDialog.setView(view);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pushToParse(VUtil.extractEditText(mTeamNameInput));
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
    }

    void pushToParse(String teamName){
        ParseObject team = new ParseObject(ParseModel.TEAM_CLASS);
        team.put(ParseModel.TEAM_NAME_COLUMN, teamName);
        team.put(ParseModel.USER_COLUMN, ParseUser.getCurrentUser());
    }
}
