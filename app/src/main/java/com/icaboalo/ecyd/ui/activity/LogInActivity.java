package com.icaboalo.ecyd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.util.VUtil;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends AppCompatActivity {

    @Bind(R.id.username_input)
    EditText mUsernameInput;

    @Bind(R.id.password_input)
    EditText mPasswordInput;

    @Bind(R.id.container)
    CoordinatorLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = ButterKnife.findById(this, R.id.app_bar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login) void loginButton(){
        if (formFilled()){
            login();
        }else {
            mUsernameInput.setError(getString(R.string.username_input_error));
            mPasswordInput.setError(getString(R.string.password_input_error));
        }
    }

    @OnClick(R.id.register) void register(){
        Intent goToRegister = new Intent(this, RegisterActivity.class);
        startActivity(goToRegister);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void login(){
        ParseUser.logInInBackground(VUtil.extractEditText(mUsernameInput), VUtil.extractEditText(mPasswordInput), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                        VUtil.goToActivity(LogInActivity.this, MainActivity.class);
                        finish();
                    VUtil.showMessage(getString(R.string.login_message) + " " + parseUser.getUsername(), mContainer);
                } else {
                    VUtil.showMessage(e.getMessage(), mContainer);
                }
            }
        });
    }

    private boolean isUsernameEmpty(){
        return VUtil.extractEditText(mUsernameInput).isEmpty();
    }

    private boolean isPasswordEmpty(){
        return VUtil.extractEditText(mPasswordInput).isEmpty();
    }

    private boolean formFilled(){
        return !(isUsernameEmpty()||isPasswordEmpty());
    }
}
