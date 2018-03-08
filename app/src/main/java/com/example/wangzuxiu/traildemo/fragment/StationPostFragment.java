package com.example.wangzuxiu.traildemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.PostListAdapter;
import com.example.wangzuxiu.traildemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationPostFragment extends Fragment {
    private RecyclerView rvPostList;
    private RecyclerView.Adapter postListAdapter;
    private RecyclerView.LayoutManager postListManager;
    private TextView tvDiscussionTopic;
    private TextView tvUserName;
    private TextView tvCreatedDate;
    private EditText etNewPost;
    private ImageButton btnPost;
    private String[] discussionThread = {"Welcome to ISS", "Zhang Peiyan", "2018-03-01"};
    private String[][] postList = {
            {"Omg", "Sriraj", "2018-03-02"}, {"Thank you", "Surbhi", "2018-03-03"},
            {"Love it", "Wang Zuxiu", "2018-03-04"}, {"Wow", "Hong Weixiang", "2018-03-05"},
            {"Wanna go", "Neelam", "2018-03-06"}};


    public StationPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_station_post, container, false);

        tvDiscussionTopic = (TextView) fragmentView.findViewById(R.id.tv_discussion_topic);
        tvUserName = (TextView) fragmentView.findViewById(R.id.tv_user_name);
        tvCreatedDate = (TextView) fragmentView.findViewById(R.id.tv_created_date);

        tvDiscussionTopic.setText(discussionThread[0]);
        tvUserName.setText(discussionThread[1]);
        tvCreatedDate.setText(discussionThread[2]);

        rvPostList = (RecyclerView) fragmentView.findViewById(R.id.post_list);
        rvPostList.setHasFixedSize(false);

        postListManager = new LinearLayoutManager(getActivity());
        rvPostList.setLayoutManager(postListManager);

        postListAdapter = new PostListAdapter(postList);
        rvPostList.setAdapter(postListAdapter);

        return fragmentView;
    }

}
