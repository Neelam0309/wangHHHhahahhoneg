package com.example.wangzuxiu.traildemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Post;
import com.example.wangzuxiu.traildemo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by mia on 05/03/18.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder>{

    //private String[][] myDataSet;
    private ArrayList<Post> myDataSet=new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPost;
        private TextView tvUserName;
        private TextView tvCreatedDate;

        private ViewHolder(View v) {
            super(v);
            tvPost = (TextView) v.findViewById(R.id.tv_post);
            tvUserName = (TextView) v.findViewById(R.id.tv_user_name);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);
        }
    }

    public PostListAdapter(ArrayList<Post> postList) {
        myDataSet = postList;
    }

    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_list, parent, false);

        return new PostListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PostListAdapter.ViewHolder viewHolder, final int position) {

        // if user who posted == trainer, his name could be different color (if want to implement this)

        viewHolder.tvPost.setText(myDataSet.get(position).post);

        String userId=myDataSet.get(position).userId;
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
        ref.child(userId).addValueEventListener(new ValueEventListener() {
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

        viewHolder.tvCreatedDate.setText(myDataSet.get(position).timestamp);
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }


}
