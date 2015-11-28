package com.icaboalo.ecyd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.ecyd.R;
import com.icaboalo.ecyd.domain.KidModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icaboalo on 11/27/2015.
 */
public class KidRecyclerAdapter extends RecyclerView.Adapter<KidRecyclerAdapter.MyKidViewHolder> {

    List<KidModel> mKidList = new ArrayList<>();
    Context mContext;
    LayoutInflater mInflater;

    public KidRecyclerAdapter(List<KidModel> kidList, Context context) {
        mKidList = kidList;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyKidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_kid, parent, false);
        return new MyKidViewHolder(view, R.id.kid_name, R.id.kid_image);
    }

    @Override
    public void onBindViewHolder(MyKidViewHolder holder, int position) {
        KidModel kid = mKidList.get(position);
        holder.setKidName(kid.getKidName());
    }

    @Override
    public int getItemCount() {
        return mKidList.size();
    }

    public class MyKidViewHolder extends RecyclerView.ViewHolder{

        TextView mKidName;
        ImageView mKidImage;

        public MyKidViewHolder(View itemView, int kidNameId, int kidImageId) {
            super(itemView);
            mKidName = (TextView) itemView.findViewById(kidNameId);
            mKidImage = (ImageView) itemView.findViewById(kidImageId);
        }

        public void setKidName(String kidName) {
            mKidName.setText(kidName);
        }
    }
}
