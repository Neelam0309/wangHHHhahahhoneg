package com.example.wangzuxiu.traildemo.Activity;

import android.app.Activity;
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

/**
 * Created by Neelam on 3/15/2018.
 */

public class EditStationActivity extends AppCompatActivity {


    private RecyclerView rvStationList;
    private RecyclerView.Adapter stationListAdapter;
    private RecyclerView.LayoutManager stationListManager;
    private FloatingActionButton fabAddStation;
    private TextView tvEmptyTrailList;
    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private String uid= FirebaseAuth.getInstance().getUid();
    private FirebaseAuth mAuth;
    private Intent intent;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_station_list);
        setContentView(R.layout.activity_station_list);

        rvStationList = (RecyclerView) findViewById(R.id.station_list);
        rvStationList.setHasFixedSize(true);

        stationListManager = new LinearLayoutManager(this);
        rvStationList.setLayoutManager(stationListManager);

        intent= getIntent();
        final String key = intent.getStringExtra("key");

        mDatabase= FirebaseDatabase.getInstance().getReference("stations");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Station> stationList=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.child(key).getChildren()){
                    Station station=child.getValue(Station.class);
                    stationList.add(station);
                }
                stationListAdapter = new StationListAdapter(stationList,true,key);
                rvStationList.setAdapter(stationListAdapter);

                tvEmptyTrailList = findViewById(R.id.tv_empty_station_list);
                // For Participant Mode, text of tvEmptyTrailList should be changed
                // tvEmptyTrailList.setText(R.string.empty_trail_list_participant); if User is participant
                tvEmptyTrailList.setVisibility(stationListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // For Participant Mode, fabAddStation and Menu-Edit(Not implemented yet) should be invisible
        fabAddStation= findViewById(R.id.fab_add_station);
        // fabAddStation.setVisibility(View.GONE); if User is participant
        fabAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                Intent intent=new Intent(EditStationActivity.this,AddNewStationActivity.class);
                intent.putExtra("key",key);
                intent.putExtra("flag",0);
                startActivity(intent);
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
        }
        return true;
    }

    private void signOut() {
        // Firebase sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(EditStationActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(EditStationActivity.this, MainActivity.class));
    }
}
