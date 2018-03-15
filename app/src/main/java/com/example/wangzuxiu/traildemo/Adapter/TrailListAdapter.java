package com.example.wangzuxiu.traildemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Activity.AddNewTrailActivity;
import com.example.wangzuxiu.traildemo.Activity.StationListActivity;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Trail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by mia on 28/02/18.
 */

public class TrailListAdapter extends RecyclerView.Adapter<TrailListAdapter.ViewHolder> {

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

    public TrailListAdapter(ArrayList<Trail> trailList, boolean editable) {
        myDataSet = trailList;
        this.editable = editable;
    }

    @Override
    public TrailListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trail_list, parent, false);

        ViewHolder holder= new ViewHolder(v, editable);
        //if it can not editable,you will go to the trail Station list

        return holder;
    }

    public void alert(Context context, final int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("make sure delete trail?");
        builder.setMessage("Are you sure to delete this trail?");
        builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                Trail trail=myDataSet.get(position);
                String Id=trail.trailDate+"-"+trail.trailName;
                final Query query=ref.child("trails").orderByChild("trailId").equalTo(Id);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()==1){
                            for(DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                                final String key=childSnapshot.getKey();
                                Log.i("tag",key);
                                FirebaseDatabase.getInstance().getReference().child("trails").child(key).removeValue();
                                String uid=FirebaseAuth.getInstance().getUid();
                                FirebaseDatabase.getInstance().getReference().child("trainer-trails").child(uid).child(key).removeValue();

                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("participant-trails");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                                            String uidKey=childSnapshot.getKey();
                                            for(DataSnapshot child:childSnapshot.getChildren()){
                                                if(child.getKey().equals(key)){
                                                    FirebaseDatabase.getInstance().getReference().child("participant-trails").child(uidKey).child(child.getKey()).removeValue();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvTrailName.setText(myDataSet.get(position).trailName);
        //viewHolder.tvTrailName.setText("shenme");
        //Log.i("tag",myDataSet.get(position).trailName);
        trailId=myDataSet.get(position).trailDate+"-"+myDataSet.get(position).trailName;
        viewHolder.tvTrailId.setText(trailId);
        viewHolder.tvTrailDate.setText(myDataSet.get(position).trailDate);
        final Context context = viewHolder.itemView.getContext();
        if (editable){
            ImageButton btnDeleteTrail = (ImageButton) viewHolder.itemView.findViewById(R.id.btn_delete_trail);
            btnDeleteTrail.setVisibility(View.VISIBLE);
            //delete a learning trail
            btnDeleteTrail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert(context,position);
                    Log.i("tag", String.valueOf(position));
                }
            });

            //update trail
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch EditTrailActivity, can be revised title and setAutoFilled from AddNewTrailActivity (not implemented yet)


                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                    trail=myDataSet.get(position);
                    String Id=trail.trailDate+"-"+trail.trailName;

                    intent = new Intent(context, AddNewTrailActivity.class);
                    //intent.putExtra("trailId",key[0]);

                    intent.putExtra("flag",1);
                    intent.putExtra("trailName",trail.trailName);
                    intent.putExtra("trailDate",trail.trailDate);
                    intent.putExtra("timestamp",trail.timestamp);
                    intent.putExtra("key",trail.key);
                    //intent.putExtra("trailId",trail.trailDate+"-"+trail.trailName);
                    context.startActivity(intent);

                }
            });

        }

        if (! editable) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationListActivity
                    final Context context = v.getContext();
                    trail=myDataSet.get(position);
                    Intent intent = new Intent(context, StationListActivity.class);
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