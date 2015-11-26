package com.icaboalo.ecyd.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.util.VUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.name_input)
    EditText mNameInput;

    @Bind(R.id.username_input)
    EditText mUsernameInput;

    @Bind({R.id.password_input, R.id.password_confirm_input})
    List<EditText> mPasswordsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = ButterKnife.findById(this, R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }

    private boolean isNameEmpty(){
        return VUtil.extractEditText(mNameInput).isEmpty();
    }

    private boolean isUsernameEmpty(){
        return VUtil.extractEditText(mUsernameInput).isEmpty();
    }

    private boolean isPasswordEmpty(){
        for (EditText password : mPasswordsInput){
            if (VUtil.extractEditText(password).isEmpty()){
                return true;
            }
        }
        return false;
    }

    private boolean passwordsMatch(){
        return VUtil.extractEditText(mPasswordsInput.get(0)).contentEquals(VUtil.extractEditText(mPasswordsInput.get(1)));
    }

}
