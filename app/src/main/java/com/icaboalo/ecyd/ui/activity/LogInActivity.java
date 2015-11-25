package com.icaboalo.ecyd.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.util.VUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends AppCompatActivity {

    @Bind(R.id.username_input)
    EditText mUsernameInput;

    @Bind(R.id.password_input)
    EditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = ButterKnife.findById(this, R.id.app_bar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login) void login(){
        if (formFilled()){
//            Intent goToMain = new Intent(this, MainActivity.class);
//            startActivity(goToMain);
        }else {
            mUsernameInput.setError(getString(R.string.username_input_error));
            mPasswordInput.setError(getString(R.string.password_input_error));
        }
    }

    @OnClick(R.id.register) void register(){
//        Intent goToRegister = new Intent(this, RegisterActivity.class);
//        startActivity(goToRegister);
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
