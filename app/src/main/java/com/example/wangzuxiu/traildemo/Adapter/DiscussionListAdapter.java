package com.example.wangzuxiu.traildemo.Adapter;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Activity.StationDetailActivity;
import com.example.wangzuxiu.traildemo.Activity.StationPostActivity;
import com.example.wangzuxiu.traildemo.R;

/**
 * Created by mia on 28/02/18.
 */

public class DiscussionListAdapter extends RecyclerView.Adapter<DiscussionListAdapter.ViewHolder>  {

    private String[][] myDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDiscussionTopic;
        private TextView tvUserName;
        private TextView tvCreatedDate;

        private ViewHolder(View v) {
            super(v);
            tvDiscussionTopic = (TextView) v.findViewById(R.id.tv_discussion_topic);
            tvUserName = (TextView) v.findViewById(R.id.tv_user_name);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationPostListActivity
                    StationDetailActivity context = (StationDetailActivity) v.getContext();
                    Intent intent = new Intent(context, StationPostActivity.class);
                    intent.putExtra("stationName", context.getTitle());
                    context.startActivity(intent);

                }
            });
        }
    }

    public DiscussionListAdapter(String[][] discussionList) {
        myDataSet = discussionList;
    }

    @Override
    public DiscussionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discussion_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // if user who posted == trainer, his name could be different color (if want to implement this)

        viewHolder.tvDiscussionTopic.setText(myDataSet[position][0]);
        viewHolder.tvUserName.setText(myDataSet[position][1]);
        viewHolder.tvCreatedDate.setText(myDataSet[position][2]);
    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }

}
