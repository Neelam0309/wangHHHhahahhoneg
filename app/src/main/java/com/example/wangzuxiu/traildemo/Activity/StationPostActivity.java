package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.PostListAdapter;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Discussion;
import com.example.wangzuxiu.traildemo.model.Post;
import com.example.wangzuxiu.traildemo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class StationPostActivity extends AppCompatActivity {
    private RecyclerView rvPostList;
    private RecyclerView.Adapter postListAdapter;
    private RecyclerView.LayoutManager postListManager;
    private TextView tvDiscussionTopic;
    private TextView tvUserName;
    private TextView tvCreatedDate;
    private EditText etNewPost;
    private ImageButton btnPost;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd hh:mm:ss");
    //private String[] discussionThread = {"Welcome to ISS", "Zhang Peiyan", "2018-03-01"};
//    private String[][] postList = {
//            {"Omg", "Sriraj", "2018-03-02"}, {"Thank you", "Surbhi", "2018-03-03"},
//            {"Love it", "Wang Zuxiu", "2018-03-04"}, {"Wow", "Hong Weixiang", "2018-03-05"},
//            {"Wanna go xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "Neelam", "2018-03-06"}};
    private ArrayList<Discussion>discussionThread=new ArrayList<>();
    private ArrayList<Post>postList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        this.setTitle(stationName);
        setContentView(R.layout.activity_station_post);

        final String discussionId=intent.getStringExtra("discussionId");
        Log.i("tag01",discussionId);
        String discussionTopic=intent.getStringExtra("discussionTopic");
        String userId=intent.getStringExtra("userId");
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
        ref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                String username=user.getUserName();
                tvUserName.setText(username);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String timestamp=intent.getStringExtra("timestamp");

        tvDiscussionTopic = (TextView) findViewById(R.id.tv_discussion_topic);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvCreatedDate = (TextView) findViewById(R.id.tv_created_date);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        tvDiscussionTopic.setText(discussionTopic);

        tvCreatedDate.setText(timestamp);

        rvPostList = (RecyclerView) findViewById(R.id.post_list);
        rvPostList.setHasFixedSize(false);

        postListManager = new LinearLayoutManager(this);
        rvPostList.setLayoutManager(postListManager);

        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("posts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList <Post> postList=new ArrayList<Post>();
                for(DataSnapshot child:dataSnapshot.child(discussionId).getChildren()){
                    Post post=child.getValue(Post.class);
                    postList.add(post);
                }
                PostListAdapter postListAdapter = new PostListAdapter(postList);
                rvPostList.setAdapter(postListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnPost=findViewById(R.id.btn_post);
        etNewPost=findViewById(R.id.et_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post=etNewPost.getText().toString();
                String uid= FirebaseAuth.getInstance().getUid();
                Date date=new Date(System.currentTimeMillis());
                String postTimestamp=formatter.format(date);
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("posts");
                String postId=ref.child(discussionId).push().getKey();
                Post temp=new Post(uid,post,postTimestamp);
                ref.child(discussionId).child(postId).setValue(temp);
                etNewPost.setText("");



            }
        });
        // Not sure how to go back to station-discussion fragment
    }
}
