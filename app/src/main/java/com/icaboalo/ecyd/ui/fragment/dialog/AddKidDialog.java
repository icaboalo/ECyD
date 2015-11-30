package com.icaboalo.ecyd.ui.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.ParseModel;
import com.icaboalo.ecyd.util.VUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 11/28/2015.
 */
public class AddKidDialog extends DialogFragment {

    @Bind(R.id.kid_name_input)
    EditText mKidNameInput;

    @Bind(R.id.team_spinner)
    Spinner mTeamSpinner;

    ArrayAdapter<String> arrayAdapter;
    String mSelectedTeam;

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
        return alertDialog.create();
    }

    private void configureDialog(AlertDialog.Builder alertDialog, View view) {
        alertDialog.setView(view);
        alertDialog.setTitle(getString(R.string.dialog_add_kid_title));
        setupSpinnerData();
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pushToParse();
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

    private void pushToParse() {
        ParseObject parseObject = new ParseObject(ParseModel.KID_CLASS);
        String user = ParseUser.getCurrentUser().getUsername();
        parseObject.put(ParseModel.USER_COLUMN, user);
        parseObject.put(ParseModel.KID_NAME_COLUMN, VUtil.extractEditText(mKidNameInput));
        parseObject.put(ParseModel.TEAM_NAME_COLUMN, mSelectedTeam);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setupSpinnerData(){
        String user = ParseUser.getCurrentUser().getUsername();
        ParseQuery<ParseObject> query = new ParseQuery<>(ParseModel.TEAM_CLASS)
                .whereContains(ParseModel.USER_COLUMN, user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                final List<String> newTeamList = new ArrayList<>();
                if(e == null){
                    for (int i = 0; i < list.size(); i++) {
                        String item = list.get(i).getString(ParseModel.TEAM_NAME_COLUMN);
                        newTeamList.add(item);
                    }
                    arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, newTeamList);
                    mTeamSpinner.setAdapter(arrayAdapter);
                    mTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mSelectedTeam = newTeamList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });
    }
}
