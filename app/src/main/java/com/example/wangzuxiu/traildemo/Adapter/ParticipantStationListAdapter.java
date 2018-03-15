package com.example.wangzuxiu.traildemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.wangzuxiu.traildemo.model.Station;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mia on 04/03/18.
 */


public class ParticipantStationListAdapter extends RecyclerView.Adapter<ParticipantStationListAdapter.ViewHolder> {

    private ArrayList<Station> myDataSet=new ArrayList<>();
    private boolean editable;
    private ImageButton btnDeleteStation;
    private String key;
    private Station station;
    private Intent intent;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tvStationName;
        private TextView tvStationSequence;
        private ImageView ivUploaded;
        private ImageButton btnAdjustUp;
        private ImageButton btnAdjustDown;
        private String key;


        private ViewHolder(View v, boolean editable, final String key) {
            super(v);
            tvStationName = (TextView) v.findViewById(R.id.tv_station_name);
            tvStationSequence = (TextView) v.findViewById(R.id.tv_station_sequence);
            ivUploaded = (ImageView) v.findViewById(R.id.iv_uploaded);
            // now just use iv_up to instead the whole button, should be an whole ImageButton
            btnAdjustUp = (ImageButton) v.findViewById(R.id.iv_up);
            btnAdjustDown = (ImageButton) v.findViewById(R.id.iv_down);
            this.key=key;


            if (! editable) {
                // btnAdjust & btnDeleteStation is invisible now
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch StationDetailActivity
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StationDetailActivity.class);
                        intent.putExtra("stationName", tvStationName.getText().toString());
                        intent.putExtra("stationKey", key);
                        context.startActivity(intent);
                    }
                });
            }

        }
    }

    public ParticipantStationListAdapter(ArrayList<Station> stationList, boolean editable,String key) {
        myDataSet = stationList;
        this.editable = editable;
        this.key=key;
    }

    @Override
    public ParticipantStationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station_list, parent, false);

        return new ViewHolder(v, editable,key);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvStationName.setText(myDataSet.get(position).getStationName());
        //viewHolder.tvStationSequence.setText(String.valueOf(position + 1) + ".");
        viewHolder.tvStationSequence.setText(String.valueOf(position+1));
        // If user is trainer, ivUploaded should be invisible
        // viewholder.ivUploaded.setVisibility(View.GONE); if user is trainer

        // Change the image if this participant have uploaded contributed item for this station
        // Now just randomly pick the image according to position, should be implemented correctly
        // The image of "√" should be changed to a nicer picture, find it in Layout item_station_list
        Resources res = viewHolder.itemView.getContext().getResources();
        final Context context = viewHolder.itemView.getContext();


        if (position % 2 == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                viewHolder.ivUploaded.setImageDrawable(res.getDrawable(android.R.drawable.checkbox_on_background, null));
            }
        }

    }


    @Override
    public int getItemCount() {
        return myDataSet.size();
    }



}