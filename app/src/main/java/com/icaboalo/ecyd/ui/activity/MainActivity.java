package com.icaboalo.ecyd.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.TeamModel;
import com.icaboalo.ecyd.ui.adapter.TeamRecyclerAdapter;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTeamRecycler();
        getTeams();
    }

    void getTeams(){
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
                            Toast.makeText(MainActivity.this, newTeamList.get(position).getTeamName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    mTeamList.setAdapter(mRecyclerAdapter);
                }
            }
        });
    }

    void setupTeamRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTeamList.setLayoutManager(linearLayoutManager);
    }

    private void isUserLogged(){
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null){
            VUtil.goToActivity(this, LogInActivity.class);
            finish();
        }
    }
}
