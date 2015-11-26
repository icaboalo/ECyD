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
import butterknife.OnClick;

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

    @OnClick(R.id.fab) void signUp(){
        if (isFormFilled() && passwordsMatch()){
//            go on...
        }else if (isNameEmpty()){
            mNameInput.setError("Please enter a Name");
        }
        else if (isUsernameEmpty()){
            mUsernameInput.setError(getString(R.string.username_input_error));
        }else if (isPasswordEmpty()){
            ButterKnife.apply(mPasswordsInput, EMPTY_ERROR);
        }else if (!passwordsMatch()){
            ButterKnife.apply(mPasswordsInput, MATCH_ERROR);
        }
    }

    private boolean isFormFilled(){
        return !(isNameEmpty() || isUsernameEmpty() || isPasswordEmpty());
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

    private final ButterKnife.Action<EditText> EMPTY_ERROR = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(EditText view, int index) {
            view.setError(getString(R.string.password_input_error));
        }
    };

    private final ButterKnife.Action<EditText> MATCH_ERROR = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(EditText view, int index) {
            view.setError(getString(R.string.password_input_match_error));
        }
    };

}
