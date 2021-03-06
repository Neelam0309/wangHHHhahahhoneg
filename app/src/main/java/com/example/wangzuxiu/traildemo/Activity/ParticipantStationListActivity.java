package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.ParticipantStationListAdapter;
import com.example.wangzuxiu.traildemo.Adapter.StationListAdapter;

import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Station;

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

public class ParticipantStationListActivity extends AppCompatActivity {
    private RecyclerView rvStationList;
    private RecyclerView.Adapter stationListAdapter;
    private RecyclerView.LayoutManager stationListManager;
    private FloatingActionButton fabAddStation;
    private TextView tvEmptyStationList;
    private GoogleSignInClient mGoogleSignInClient;
    private ArrayList<Station> stationList=new ArrayList<>();
    private String uid= FirebaseAuth.getInstance().getUid();
    private FirebaseAuth mAuth;
    String key;
    Intent intent;
    // private String[] stationList = {"ISS Level 1", "ISS Level 2",
    //          "ISS Level 3", "ISS Level 4", "ISS Level 5", "ISS Level 6", "ISS Level 7",
    //       "ISS Level 8", "ISS Level 9", "ISS Level 10"};
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_station_list);
        setContentView(R.layout.activity_station_list);


        rvStationList = (RecyclerView) findViewById(R.id.station_list);
        rvStationList.setHasFixedSize(false);

        stationListManager = new LinearLayoutManager(this);
        rvStationList.setLayoutManager(stationListManager);
        intent = getIntent();
        key = intent.getStringExtra("key");
        intent.putExtra("key",key);


        //  stationListAdapter = new StationListAdapter(stationList,false);
        //  rvStationList.setAdapter(stationListAdapter);

        mDatabase= FirebaseDatabase.getInstance().getReference("stations");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stationList =new ArrayList<Station>();
                for(DataSnapshot child:dataSnapshot.child(key).getChildren()){
                    Station station=child.getValue(Station.class);
                    stationList.add(station);
                }
                key = intent.getStringExtra("key");
                stationListAdapter = new ParticipantStationListAdapter(stationList,false,key);
                rvStationList.setAdapter(stationListAdapter);

                System.out.println(stationList);
                // For Participant Mode, text of tvEmptyTrailList should be changed
                // tvEmptyTrailList.setText(R.string.empty_trail_list_participant); if User is participant
                tvEmptyStationList = findViewById(R.id.tv_empty_station_list);
                tvEmptyStationList.setVisibility(stationListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fabAddStation = findViewById(R.id.fab_add_station);
        fabAddStation.setVisibility(View.GONE);

        // For Participant Mode, fabAddTrail and Menu-Edit(Not implemented yet) should be invisible

        // When trainer click the menu - Edit, the trailListAdapter should be changed to EditableTrailListAdapter
        // Not implemented yet
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if user is participant then
        // getMenuInflater().inflate(R.menu.menu_user, menu);
        // if user is trainer then
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            signOut();

        }
        return true;
    }
    private void signOut() {
        // Firebase sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(ParticipantStationListActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(ParticipantStationListActivity.this,MainActivity.class));
    }


}
