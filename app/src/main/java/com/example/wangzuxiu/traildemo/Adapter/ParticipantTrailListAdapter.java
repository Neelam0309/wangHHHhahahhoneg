package com.example.wangzuxiu.traildemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Activity.ParticipantStationListActivity;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Trail;

import java.util.ArrayList;


/**
 * Created by mia on 28/02/18.
 */

public class ParticipantTrailListAdapter extends RecyclerView.Adapter<ParticipantTrailListAdapter.ViewHolder> {

    private ArrayList<Trail> myDataSet=new ArrayList<>();
    private boolean editable;
    private Context context;
    private String trailId;
    private Trail trail;
    private Intent intent;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTrailName;
        private TextView tvTrailId;
        private TextView tvTrailDate;
        private ImageButton btnDeleteTrail;

        private ViewHolder(View v, boolean editable) {
            super(v);
            tvTrailName = (TextView) v.findViewById(R.id.tv_trail_name);
            tvTrailId = (TextView) v.findViewById(R.id.tv_trail_id);
            tvTrailDate = (TextView) v.findViewById(R.id.tv_trail_date);
            btnDeleteTrail = (ImageButton) v.findViewById(R.id.btn_delete_trail);

        }
    }

    public ParticipantTrailListAdapter(ArrayList<Trail> trailList, boolean editable) {
        myDataSet = trailList;
        this.editable = editable;
    }

    @Override
    public ParticipantTrailListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trail_list, parent, false);

        ViewHolder holder= new ViewHolder(v, editable);
        //if it can not editable,you will go to the trail Station list

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        viewHolder.tvTrailName.setText(myDataSet.get(position).trailName);
        //viewHolder.tvTrailName.setText("shenme");
        //Log.i("tag",myDataSet.get(position).trailName);
        trailId=myDataSet.get(position).trailDate+"-"+myDataSet.get(position).trailName;
        viewHolder.tvTrailId.setText(trailId);
        viewHolder.tvTrailDate.setText(myDataSet.get(position).trailDate);
        final Context context = viewHolder.itemView.getContext();


        if (! editable) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationListActivity
                    final Context context = v.getContext();
                    trail=myDataSet.get(position);
                    Intent intent = new Intent(context, ParticipantStationListActivity.class);
                    intent.putExtra("key",trail.key);
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        //return myDataSet==null ? 0 : myDataSet.size();
        return  myDataSet.size();
    }



}