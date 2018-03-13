package com.example.wangzuxiu.traildemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzuxiu.traildemo.Activity.AddNewTrailActivity;
import com.example.wangzuxiu.traildemo.Activity.StationListActivity;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Trail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mia on 28/02/18.
 */

public class TrailListAdapter extends RecyclerView.Adapter<TrailListAdapter.ViewHolder>  {

    private ArrayList<Trail> myDataSet=new ArrayList<>();
    private boolean editable;
    private Context context;
    private String trailId;

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
            Context context=v.getContext();

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
        if (! editable) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationListActivity
                    final Context context = v.getContext();
                    Intent intent = new Intent(context, StationListActivity.class);;
                    context.startActivity(intent);
                }
            });
        }


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
                String Id=trail.timestamp+"-"+trail.trailName;
                Log.i("tag",Id);
                ref.child("trails").child(Id).removeValue();
                String uid=FirebaseAuth.getInstance().getUid();
                ref.child("trainer-trails").child(uid).child(Id).removeValue();

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

            btnDeleteTrail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert(context,position);
                    Log.i("tag", String.valueOf(position));
                }
            });


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        //return myDataSet==null ? 0 : myDataSet.size();
        return  myDataSet.size();
    }



}