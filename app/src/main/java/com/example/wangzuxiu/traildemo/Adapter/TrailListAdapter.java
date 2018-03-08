package com.example.wangzuxiu.traildemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzuxiu.traildemo.Activity.AddNewTrailActivity;
import com.example.wangzuxiu.traildemo.Activity.StationListActivity;
import com.example.wangzuxiu.traildemo.R;


/**
 * Created by mia on 28/02/18.
 */

public class TrailListAdapter extends RecyclerView.Adapter<TrailListAdapter.ViewHolder>  {

    private String[][] myDataSet;
    private boolean editable;

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

            final Context context = v.getContext();

            if (! editable) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch StationListActivity
                        Intent intent = new Intent(context, StationListActivity.class);;
                        context.startActivity(intent);
                    }
                });
            } else if (editable){
                btnDeleteTrail.setVisibility(View.VISIBLE);
                btnDeleteTrail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // should be a dialog to ensure delete or not
                        Toast.makeText(context, "delete learning trail?", Toast.LENGTH_SHORT).show();

                    }
                });

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch EditTrailActivity, can be revised title and setAutoFilled from AddNewTrailActivity (not implemented yet)
                        Context context = v.getContext();
                        Intent intent = new Intent(context, AddNewTrailActivity.class);;
                        context.startActivity(intent);
                    }
                });
            }

        }
    }

    public TrailListAdapter(String[][] trailList, boolean editable) {
        myDataSet = trailList;
        this.editable = editable;
    }

    @Override
    public TrailListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trail_list, parent, false);

        return new ViewHolder(v, editable);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvTrailName.setText(myDataSet[position][0]);
        viewHolder.tvTrailId.setText(myDataSet[position][1]);
        viewHolder.tvTrailDate.setText(myDataSet[position][2]);
    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }

}