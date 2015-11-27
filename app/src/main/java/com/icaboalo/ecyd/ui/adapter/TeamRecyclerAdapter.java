package com.icaboalo.ecyd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.TeamModel;
import com.icaboalo.ecyd.util.ViewHolderClick;

import java.util.List;

/**
 * Created by icaboalo on 11/26/2015.
 */
public class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.MyTeamViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    List<TeamModel> mTeamModelList;
    ViewHolderClick mViewHolderClick;

    public TeamRecyclerAdapter(Context context, List<TeamModel> teamModelList, ViewHolderClick onClickListener) {
        mContext = context;
        mTeamModelList = teamModelList;
        mViewHolderClick = onClickListener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyTeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_team, parent, false);
        return new MyTeamViewHolder(view, R.id.team_name, mViewHolderClick);
    }

    @Override
    public void onBindViewHolder(MyTeamViewHolder holder, int position) {
        TeamModel teamModel = mTeamModelList.get(position);
        holder.setTeamName(teamModel.getTeamName());
    }

    @Override
    public int getItemCount() {
        return mTeamModelList.size();
    }

    class MyTeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTeamName;
        ViewHolderClick mViewHolderClick;

        public MyTeamViewHolder(View itemView, int teamNameId, ViewHolderClick onClickListener) {
            super(itemView);
            mTeamName = (TextView) itemView.findViewById(teamNameId);
            mViewHolderClick = onClickListener;
            itemView.setOnClickListener(this);
        }

        public void setTeamName(String teamName) {
            mTeamName.setText(teamName);
        }

        @Override
        public void onClick(View v) {
            mViewHolderClick.onClick(v, getAdapterPosition());
        }
    }
}
