package com.example.wangzuxiu.traildemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;

/**
 * Created by mia on 05/03/18.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private String[][] myDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // just static data now
        private ImageView ivFile;
        private TextView tvParticipantName;
        private TextView tvDescription;
        private TextView tvCreatedDate;


        private ViewHolder(View v) {
            super(v);
            ivFile = (ImageView) v.findViewById(R.id.iv_file);
            tvParticipantName = (TextView) v.findViewById(R.id.tv_participant_name);
            tvDescription = (TextView) v.findViewById(R.id.tv_description);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // View relevant file
                }
            });
        }
    }

    public ItemListAdapter(String[][] itemList) {
        myDataSet = itemList;
    }

    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item_list, parent, false);

        return new ItemListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemListAdapter.ViewHolder viewHolder, int position) {
        // ivFile set Thumbnail for each file according to its file type (not implemented yet)

        viewHolder.tvDescription.setText(myDataSet[position][1]);
        viewHolder.tvParticipantName.setText(myDataSet[position][2]);
        viewHolder.tvCreatedDate.setText(myDataSet[position][3]);

    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }
}
