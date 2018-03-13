package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.TrailListAdapter;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Trail;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 04/03/18.
 */

public class TrailListActivity extends AppCompatActivity{

    private RecyclerView rvTrailList;
    private RecyclerView.Adapter trailListAdapter;
    private RecyclerView.LayoutManager trailListManager;
    private FloatingActionButton fabAddTrail;
    private TextView tvEmptyTrailList;
    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private String uid= FirebaseAuth.getInstance().getUid();
    private FirebaseAuth mAuth;

    private ArrayList<Trail> trailList=new ArrayList<Trail>();
    //private String[][] trailList = {{"Tour to ISS", "20180301-ISS", "2018-03-01"}, {"Tour to NUS", "20180401-NUS", "2018-04-01"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_trail_list);
        setContentView(R.layout.activity_trail_list);

        rvTrailList = (RecyclerView) findViewById(R.id.trail_list);
        rvTrailList.setHasFixedSize(true);

        trailListManager = new LinearLayoutManager(this);
        rvTrailList.setLayoutManager(trailListManager);

        mDatabase= FirebaseDatabase.getInstance().getReference("trainer-trails");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList <Trail> trailList=new ArrayList<Trail>();
                for(DataSnapshot child:dataSnapshot.child(uid).getChildren()){
                    Trail trail=child.getValue(Trail.class);
                    trailList.add(trail);
                }
                trailListAdapter = new TrailListAdapter(trailList,false);
                rvTrailList.setAdapter(trailListAdapter);

                tvEmptyTrailList = findViewById(R.id.tv_empty_trail_list);
                // For Participant Mode, text of tvEmptyTrailList should be changed
                // tvEmptyTrailList.setText(R.string.empty_trail_list_participant); if User is participant
                tvEmptyTrailList.setVisibility(trailListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // For Participant Mode, fabAddTrail and Menu-Edit(Not implemented yet) should be invisible
        fabAddTrail = findViewById(R.id.fab_add_trail);
        // fabAddTrail.setVisibility(View.GONE); if User is participant
        fabAddTrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrailListActivity.this, AddNewTrailActivity.class));
            }
        });

        // When trainer click the menu - Edit, the trailListAdapter should be changed to EditableTrailListAdapter
        // Not implemented yet
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if user is participant then
        // getMenuInflater().inflate(R.menu.menu_user, menu);
        // if user is trainer then
        getMenuInflater().inflate(R.menu.menu_trainer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            signOut();

        } else if (i == R.id.action_edit) {

            mDatabase = FirebaseDatabase.getInstance().getReference("trainer-trails");

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Trail> trailList = new ArrayList<Trail>();
                    for (DataSnapshot child : dataSnapshot.child(uid).getChildren()) {
                        Trail trail = child.getValue(Trail.class);
                        trailList.add(trail);
                    }
                    trailListAdapter = new TrailListAdapter(trailList, true);
                    rvTrailList.setAdapter(trailListAdapter);

                    tvEmptyTrailList = findViewById(R.id.tv_empty_trail_list);
                    // For Participant Mode, text of tvEmptyTrailList should be changed
                    // tvEmptyTrailList.setText(R.string.empty_trail_list_participant); if User is participant
                    tvEmptyTrailList.setVisibility(trailListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return true;
    }

    private void signOut() {
        // Firebase sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(TrailListActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(TrailListActivity.this, MainActivity.class));
    }


}



