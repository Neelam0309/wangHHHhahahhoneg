package com.example.wangzuxiu.traildemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wangzuxiu.traildemo.R;


/**
 * Created by mia on 05/03/18.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder>{

    private String[][] myDataSet;

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

    public PostListAdapter(String[][] postList) {
        myDataSet = postList;
    }

    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_list, parent, false);

        return new PostListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostListAdapter.ViewHolder viewHolder, int position) {

        // if user who posted == trainer, his name could be different color (if want to implement this)

        viewHolder.tvPost.setText(myDataSet[position][0]);
        viewHolder.tvUserName.setText(myDataSet[position][1]);
        viewHolder.tvCreatedDate.setText(myDataSet[position][2]);
    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }


}
