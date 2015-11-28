package com.icaboalo.ecyd.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.KidModel;
import com.icaboalo.ecyd.domain.ParseModel;
import com.icaboalo.ecyd.ui.adapter.KidRecyclerAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TeamInfoActivity extends AppCompatActivity {

    @Bind(R.id.kids_list)
    RecyclerView mKidList;

    KidRecyclerAdapter mKidRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecycler();
        getKids();
    }

    void setupRecycler(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TeamInfoActivity.this, 2, LinearLayoutManager.VERTICAL, false);
        mKidList.setLayoutManager(gridLayoutManager);
    }

    void getKids(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseModel.KID_CLASS);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null){
                    List<KidModel> newKidList = new ArrayList<KidModel>();
                    for (int i = 0; i < list.size(); i++) {
                        String kidName = list.get(i).getString("kid_name");
                        newKidList.add(new KidModel(kidName));
                    }
                    mKidRecyclerAdapter = new KidRecyclerAdapter(newKidList, TeamInfoActivity.this);
                    mKidList.setAdapter(mKidRecyclerAdapter);
                }
            }
        });
    }
}
