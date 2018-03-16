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

import com.example.wangzuxiu.traildemo.Adapter.DiscussionListAdapter;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Discussion;
import com.example.wangzuxiu.traildemo.model.Trail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StationDiscussionFragment extends Fragment {
    private RecyclerView rvDiscussionList;
    private RecyclerView.Adapter discussionListAdapter;
    private RecyclerView.LayoutManager discussionListManager;
    private EditText etNewDiscussion;
    private DatabaseReference mDatabase;
    private TextView tvEmptyTrailList;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd hh:mm:ss");
    private ImageButton btnPost;
    private ArrayList<Discussion> discussionList=new ArrayList<Discussion>();
//    private String[][] discussionList = {{"Welcome to ISS", "Zhang Peiyan", "2018-03-01"},
//            {"Great", "Sriraj", "2018-03-02"}, {"Good Good Good Good Good Good Good Good Good Good Good Good Good", "Surbhi", "2018-03-03"},
//            {"I like it", "Wang Zuxiu", "2018-03-04"}, {"Where is it", "Hong Weixiang", "2018-03-05"},
//            {"Let's go together", "Neelam xxxxxxxxxxxxxxxxxxxxxxxxxxx", "2018-03-06"}};


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

        final View fragmentView = inflater.inflate(R.layout.fragment_station_discussion, container, false);

        rvDiscussionList = (RecyclerView) fragmentView.findViewById(R.id.discussion_list);
        rvDiscussionList.setHasFixedSize(false);

        discussionListManager = new LinearLayoutManager(getActivity());
        rvDiscussionList.setLayoutManager(discussionListManager);

        Bundle bundle=new Bundle();
        bundle=this.getArguments();
        final String stationId=bundle.getString("stationId");


        btnPost=fragmentView.findViewById(R.id.btn_post);
        etNewDiscussion=fragmentView.findViewById(R.id.et_discussion);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic=etNewDiscussion.getText().toString();
                String uid= FirebaseAuth.getInstance().getUid();
                Date date=new Date(System.currentTimeMillis());
                String discussionTimestamp=formatter.format(date);
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("discussions");
                String discussionKey=ref.child(stationId).push().getKey();
                Discussion discussion=new Discussion(uid,topic,discussionTimestamp.toString(),discussionKey);
                ref.child(stationId).child(discussionKey).setValue(discussion);
                etNewDiscussion.setText("");



            }
        });

        mDatabase= FirebaseDatabase.getInstance().getReference("discussions");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Discussion> discussionList=new ArrayList<Discussion>();
                for(DataSnapshot child:dataSnapshot.child(stationId).getChildren()){
                    Discussion discussion=child.getValue(Discussion.class);
                    discussionList.add(discussion);

                }
                discussionListAdapter= new DiscussionListAdapter(discussionList);
                rvDiscussionList.setAdapter(discussionListAdapter);

                tvEmptyTrailList = fragmentView.findViewById(R.id.tv_empty_discussion);
                // For Participant Mode, text of tvEmptyTrailList should be changed
                // tvEmptyTrailList.setText(R.string.empty_trail_list_participant); if User is participant
                tvEmptyTrailList.setVisibility(discussionListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return fragmentView;

    }


}
