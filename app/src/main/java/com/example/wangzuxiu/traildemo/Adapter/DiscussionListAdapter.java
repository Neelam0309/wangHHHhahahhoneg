package com.example.wangzuxiu.traildemo.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Activity.StationDetailActivity;
import com.example.wangzuxiu.traildemo.Activity.StationPostActivity;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Discussion;
import com.example.wangzuxiu.traildemo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mia on 28/02/18.
 */

public class DiscussionListAdapter extends RecyclerView.Adapter<DiscussionListAdapter.ViewHolder>  {

    //private String[][] myDataSet;
    private ArrayList<Discussion> myDataSet=new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDiscussionTopic;
        private TextView tvUserName;
        private TextView tvCreatedDate;

        private ViewHolder(View v) {
            super(v);
            tvDiscussionTopic = (TextView) v.findViewById(R.id.tv_discussion_topic);
            tvUserName = (TextView) v.findViewById(R.id.tv_user_name);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);

        }
    }

    public DiscussionListAdapter(ArrayList<Discussion>discussionList) {
        myDataSet = discussionList;
    }

    @Override
    public DiscussionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discussion_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // if user who posted == trainer, his name could be different color (if want to implement this)

        viewHolder.tvDiscussionTopic.setText(myDataSet.get(position).topic);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");

        String uid=myDataSet.get(position).userId;

        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                String username=user.getUserName();
                viewHolder.tvUserName.setText(username);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch StationPostListActivity
                StationDetailActivity context = (StationDetailActivity) v.getContext();
                Discussion discussion=myDataSet.get(position);

                Intent intent = new Intent(context, StationPostActivity.class);
                intent.putExtra("discussionTopic",myDataSet.get(position).topic);
                intent.putExtra("userId",myDataSet.get(position).userId);
                intent.putExtra("timestamp",myDataSet.get(position).timestamp);
                intent.putExtra("discussionId",myDataSet.get(position).discussionId);
                Log.i("tag",myDataSet.get(position).discussionId);
                intent.putExtra("stationName", context.getTitle());
                context.startActivity(intent);

            }
        });
        //Log.i("tag11", username[0]);

        viewHolder.tvCreatedDate.setText((myDataSet.get(position).timestamp).toString());
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

}
