package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.PostListAdapter;
import com.example.wangzuxiu.traildemo.R;


public class StationPostActivity extends AppCompatActivity {
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
            {"Wanna go xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "Neelam", "2018-03-06"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        this.setTitle(stationName);
        setContentView(R.layout.activity_station_post);

        tvDiscussionTopic = (TextView) findViewById(R.id.tv_discussion_topic);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvCreatedDate = (TextView) findViewById(R.id.tv_created_date);

        tvDiscussionTopic.setText(discussionThread[0]);
        tvUserName.setText(discussionThread[1]);
        tvCreatedDate.setText(discussionThread[2]);

        rvPostList = (RecyclerView) findViewById(R.id.post_list);
        rvPostList.setHasFixedSize(false);

        postListManager = new LinearLayoutManager(this);
        rvPostList.setLayoutManager(postListManager);

        postListAdapter = new PostListAdapter(postList);
        rvPostList.setAdapter(postListAdapter);

        // Not sure how to go back to station-discussion fragment
    }
}
