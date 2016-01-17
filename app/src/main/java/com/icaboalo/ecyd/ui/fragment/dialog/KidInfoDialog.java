package com.icaboalo.ecyd.ui.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.ParseModel;
import com.icaboalo.ecyd.domain.constant.SharedPreferencesConstants;
import com.icaboalo.ecyd.util.VUtil;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 11/29/2015.
 */
public class KidInfoDialog extends DialogFragment {

    String mObjectId;

    @Bind(R.id.kid_name)
    TextView mKidName;

    @Bind(R.id.team_name)
    TextView mTeamName;

    @Bind(R.id.assistance_checkbox)
    CheckBox mAssistance;

    @Bind(R.id.talk_checkbox)
    CheckBox mTalk;

    public static KidInfoDialog newInstance(){
        KidInfoDialog fragment = new KidInfoDialog();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = VUtil.inflateView(getActivity(), R.layout.dialog_kid_info);
        ButterKnife.bind(this, view);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        configureDialog(alertDialog, view);
        return alertDialog.create();
    }

    void configureDialog(AlertDialog.Builder alertDialog, View view){
        alertDialog.setView(view);
        getKidFromParse(getKid());
        alertDialog.setTitle(getString(R.string.dialog_add_team_title));
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveOnParse(mObjectId, false);
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveOnParse(mObjectId, true);
            }
        });
        alertDialog.setCancelable(true);
    }

    private void saveOnParse(String objectId, final boolean delete) {
        ParseQuery<ParseObject> query = new ParseQuery<>(ParseModel.KID_CLASS);
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null){
                    if (delete == true){
                        parseObject.deleteInBackground();
                    }else {
                        parseObject.put(ParseModel.TALK_COLUMN, mTalk.isChecked());
                        parseObject.put(ParseModel.ASSISTANCE_COLUMN, mAssistance.isChecked());
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null){
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    void getKidFromParse(String kidName){
        ParseQuery<ParseObject> query = new ParseQuery<>(ParseModel.KID_CLASS).whereContains(ParseModel.KID_NAME_COLUMN, kidName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null){
                    for (int i = 0; i < list.size(); i++) {
                        mObjectId = list.get(i).getObjectId();
                        String kidName = list.get(i).getString(ParseModel.KID_NAME_COLUMN);
                        String teamName = list.get(i).getString(ParseModel.TEAM_NAME_COLUMN);
                        boolean assistance = list.get(i).getBoolean(ParseModel.ASSISTANCE_COLUMN);
                        boolean talk = list.get(i).getBoolean(ParseModel.TALK_COLUMN);
                        VUtil.setText(mKidName, kidName);
                        VUtil.setText(mTeamName, teamName);
                        VUtil.setChecked(mAssistance, assistance);
                        VUtil.setChecked(mTalk, talk);
                    }
                }
            }
        });
    }

    String getKid(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.FILE_KID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPreferencesConstants.KID_NAME, "");
    }
}
