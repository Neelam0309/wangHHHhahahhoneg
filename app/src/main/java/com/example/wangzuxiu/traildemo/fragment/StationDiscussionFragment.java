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

import com.example.wangzuxiu.traildemo.Adapter.DiscussionListAdapter;
import com.example.wangzuxiu.traildemo.R;

public class StationDiscussionFragment extends Fragment {
    private RecyclerView rvDiscussionList;
    private RecyclerView.Adapter discussionListAdapter;
    private RecyclerView.LayoutManager discussionListManager;
    private EditText etNewDiscussion;
    private ImageButton btnPost;
    private String[][] discussionList = {{"Welcome to ISS", "Zhang Peiyan", "2018-03-01"},
            {"Great", "Sriraj", "2018-03-02"}, {"Good Good Good Good Good Good Good Good Good Good Good Good Good", "Surbhi", "2018-03-03"},
            {"I like it", "Wang Zuxiu", "2018-03-04"}, {"Where is it", "Hong Weixiang", "2018-03-05"},
            {"Let's go together", "Neelam xxxxxxxxxxxxxxxxxxxxxxxxxxx", "2018-03-06"}};


    public StationDiscussionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_station_discussion, container, false);

        rvDiscussionList = (RecyclerView) fragmentView.findViewById(R.id.discussion_list);
        rvDiscussionList.setHasFixedSize(false);

        discussionListManager = new LinearLayoutManager(getActivity());
        rvDiscussionList.setLayoutManager(discussionListManager);

        discussionListAdapter = new DiscussionListAdapter(discussionList);
        rvDiscussionList.setAdapter(discussionListAdapter);

        return fragmentView;

    }

}
