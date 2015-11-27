package com.icaboalo.ecyd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.TeamModel;

import java.util.List;

/**
 * Created by icaboalo on 11/26/2015.
 */
public class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.MyTeamViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    List<TeamModel> mTeamModelList;

    public TeamRecyclerAdapter(Context context, List<TeamModel> teamModelList) {
        mContext = context;
        mTeamModelList = teamModelList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyTeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_team, parent, false);
        return new MyTeamViewHolder(view, R.id.team_name);
    }

    @Override
    public void onBindViewHolder(MyTeamViewHolder holder, int position) {
        TeamModel teamModel = mTeamModelList.get(position);
        holder.setTeamName(teamModel.getTeamName());
    }

    public void changeData(List<TeamModel> newTeamList){
        mTeamModelList = newTeamList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTeamModelList.size();
    }

    class MyTeamViewHolder extends RecyclerView.ViewHolder{

        TextView mTeamName;

        public MyTeamViewHolder(View itemView, int teamNameId) {
            super(itemView);
            mTeamName = (TextView) itemView.findViewById(teamNameId);
        }

        public void setTeamName(String teamName) {
            mTeamName.setText(teamName);
        }
    }
}
