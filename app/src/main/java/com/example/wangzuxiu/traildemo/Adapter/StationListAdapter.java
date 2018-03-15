package com.example.wangzuxiu.traildemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
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


public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.ViewHolder> {

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
        private ImageView btnprogress;
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
            btnprogress=v.findViewById(R.id.iv_uploaded);

            btnprogress.setVisibility(View.GONE);
            if (! editable) {
                // btnAdjust & btnDeleteStation is invisible now
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch StationDetailActivity
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StationDetailActivity.class);
                        intent.putExtra("stationName", tvStationName.getText().toString());
                        intent.putExtra("trailKey", key);
                        context.startActivity(intent);
                    }
                });
            } else if (editable) {
                // btnAdjust & btnDeleteStation is visible now
                btnAdjustUp.setVisibility(View.VISIBLE);
                btnAdjustDown.setVisibility(View.VISIBLE);
                //btnDeleteStation.setVisibility(View.VISIBLE);
                tvStationSequence.setVisibility(View.INVISIBLE);
                ivUploaded.setVisibility(View.INVISIBLE);

                final Context context = v.getContext();
                // OnClick btnAdjust to change sequence (not implemented yet)
                // OnClick btnDeleteStation to delete station
               /*// btnDeleteStation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // should be a dialog to ensure delete or not

                       // Toast.makeText(context, "delete learning station?", Toast.LENGTH_SHORT).show();

                    }
                });*/

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

    public StationListAdapter(ArrayList<Station> stationList, boolean editable,String key) {
        myDataSet = stationList;
        this.editable = editable;
        this.key=key;
    }

    @Override
    public StationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if(editable) {
            btnDeleteStation = (ImageButton) viewHolder.itemView.findViewById(R.id.btn_delete_station);
            btnDeleteStation.setVisibility(View.VISIBLE);

            btnDeleteStation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert(context,position);
                    Log.i("tag", String.valueOf(position));
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                    station=myDataSet.get(position);
                    intent = new Intent(context, AddNewStationActivity.class);
                    //intent.putExtra("trailId",key[0]);
                    System.out.println("station name"+station.getStationName());
                    intent.putExtra("flag",1);
                    intent.putExtra("stationName",station.getStationName());
                    intent.putExtra("location",station.getGps());
                    System.out.println("location:"+station.getGps());
                    intent.putExtra("instructions",station.getInstructions());
                    intent.putExtra("key",station.getStationKey());

                    context.startActivity(intent);
                }
            });

        }


//        if (position % 2 == 0) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                viewHolder.ivUploaded.setImageDrawable(res.getDrawable(android.R.drawable.checkbox_on_background, null));
//            }
//        }

    }

    public void alert(Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Delete this station?");
        final Context context1 = context;
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("tag","in on click");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                final Station station = myDataSet.get(position);
                //Log.i("tag11100",key);

                Query query = ref.child("stations").child(key).orderByChild("stationName").equalTo(station.getStationName());
                Log.i("tag11101",station.getStationName());
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("tag11100", String.valueOf(dataSnapshot.getChildrenCount()));
                        if(dataSnapshot.getChildrenCount()==1)
                        {

                            for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                            {
                                String key1 = childSnapshot.getKey();

                                FirebaseDatabase.getInstance().getReference("stations").child(key).child(key1).removeValue();
                                Toast.makeText(context1, station.getStationName()+" deleted successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // String Id = trailID;
                // Log.i("tag", Id);



            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return myDataSet.size();
    }







}