package com.icaboalo.ecyd.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.TeamModel;
import com.icaboalo.ecyd.domain.constant.SharedPreferencesConstants;
import com.icaboalo.ecyd.ui.adapter.TeamRecyclerAdapter;
import com.icaboalo.ecyd.ui.fragment.dialog.AddTeamDialog;
import com.icaboalo.ecyd.util.VUtil;
import com.icaboalo.ecyd.util.ViewHolderClick;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.team_list)
    RecyclerView mTeamList;

    TeamRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        isUserLogged();
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTeamRecycler();
        getTeams();
    }

    @OnClick(R.id.fab) void addTeam(){
        showDialog();
    }

    private void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddTeamDialog addTeamDialog = new AddTeamDialog().newInstance();
        addTeamDialog.show(fragmentManager, "AddTeam");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_log_out):
                ParseUser.logOut();
                isUserLogged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void getTeams() {
        String user = ParseUser.getCurrentUser().getUsername();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Teams").whereContains("user", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    final List<TeamModel> newTeamList = new ArrayList<TeamModel>();
                    for (int i = 0; i < list.size(); i++) {
                        String item = list.get(i).getString("team_name");
                        newTeamList.add(new TeamModel(item));
                    }
                    mRecyclerAdapter = new TeamRecyclerAdapter(MainActivity.this, newTeamList, new ViewHolderClick() {
                        @Override
                        public void onClick(View view, int position) {
                            saveOnSharedPrefeces(newTeamList.get(position).getTeamName());
                            VUtil.goToActivity(MainActivity.this, TeamInfoActivity.class);
                        }
                    });
                    mTeamList.setAdapter(mRecyclerAdapter);
                }
            }
        });
    }

    void setupTeamRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTeamList.setLayoutManager(linearLayoutManager);
    }

    private void isUserLogged() {
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            VUtil.goToActivity(this, LogInActivity.class);
            finish();
        }
    }

    private void saveOnSharedPrefeces(String team){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesConstants.FILE__TEAM, MODE_PRIVATE);
        sharedPreferences.edit().putString(SharedPreferencesConstants.TEAM_NAME, team).apply();
    }
}
