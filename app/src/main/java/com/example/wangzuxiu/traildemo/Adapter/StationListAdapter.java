package com.example.wangzuxiu.traildemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzuxiu.traildemo.Activity.AddNewStationActivity;
import com.example.wangzuxiu.traildemo.Activity.StationDetailActivity;
import com.example.wangzuxiu.traildemo.R;

/**
 * Created by mia on 04/03/18.
 */


public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.ViewHolder>  {

    private String[] myDataSet;
    private boolean editable;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tvStationName;
        private TextView tvStationSequence;
        private ImageView ivUploaded;
        private ImageButton btnAdjustUp;
        private ImageButton btnAdjustDown;
        private ImageButton btnDeleteStation;

        private ViewHolder(View v, boolean editable) {
            super(v);
            tvStationName = (TextView) v.findViewById(R.id.tv_station_name);
            tvStationSequence = (TextView) v.findViewById(R.id.tv_station_sequence);
            ivUploaded = (ImageView) v.findViewById(R.id.iv_uploaded);
            // now just use iv_up to instead the whole button, should be an whole ImageButton
            btnAdjustUp = (ImageButton) v.findViewById(R.id.iv_up);
            btnAdjustDown = (ImageButton) v.findViewById(R.id.iv_down);
            btnDeleteStation = (ImageButton) v.findViewById(R.id.btn_delete_station);

            if (! editable) {
                // btnAdjust & btnDeleteStation is invisible now
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch StationDetailActivity
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StationDetailActivity.class);
                        intent.putExtra("stationName", tvStationName.getText().toString());
                        context.startActivity(intent);
                    }
                });
            } else if (editable) {
                // btnAdjust & btnDeleteStation is visible now
                btnAdjustUp.setVisibility(View.VISIBLE);
                btnAdjustDown.setVisibility(View.VISIBLE);
                btnDeleteStation.setVisibility(View.VISIBLE);
                tvStationSequence.setVisibility(View.INVISIBLE);
                ivUploaded.setVisibility(View.INVISIBLE);

                final Context context = v.getContext();
                // OnClick btnAdjust to change sequence (not implemented yet)
                // OnClick btnDeleteStation to delete station
                btnDeleteStation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // should be a dialog to ensure delete or not
                        Toast.makeText(context, "delete learning station?", Toast.LENGTH_SHORT).show();

                    }
                });

                // OnClick Item
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch EditStationActivity, can be revised title and setAutoFilled from AddNewTrailActivity (not implemented yet)
                        Intent intent = new Intent(context, AddNewStationActivity.class);
                        // intent.putExtra("stationName", tvStationName.getText().toString());
                        context.startActivity(intent);
                    }
                });


            }

        }
    }

    public StationListAdapter(String[] stationList, boolean editable) {
        myDataSet = stationList;
        this.editable = editable;
    }

    @Override
    public StationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station_list, parent, false);

        return new ViewHolder(v, editable);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvStationName.setText(myDataSet[position]);
        viewHolder.tvStationSequence.setText(String.valueOf(position + 1) + ".");

        // If user is trainer, ivUploaded should be invisible
        // viewholder.ivUploaded.setVisibility(View.GONE); if user is trainer

        // Change the image if this participant have uploaded contributed item for this station
        // Now just randomly pick the image according to position, should be implemented correctly
        // The image of "√" should be changed to a nicer picture, find it in Layout item_station_list
        Resources res = viewHolder.itemView.getContext().getResources();

        if (position % 2 == 0) {
            viewHolder.ivUploaded.setImageDrawable(res.getDrawable(android.R.drawable.checkbox_on_background, null));
        }

    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }
}

