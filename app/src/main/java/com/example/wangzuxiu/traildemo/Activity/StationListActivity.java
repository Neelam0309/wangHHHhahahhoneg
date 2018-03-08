package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
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

public class StationListActivity extends AppCompatActivity {
    private RecyclerView rvStationList;
    private RecyclerView.Adapter stationListAdapter;
    private RecyclerView.LayoutManager stationListManager;
    private FloatingActionButton fabAddStation;
    private TextView tvEmptyStationList;
    private String[] stationList = {"ISS Level 1", "ISS Level 2",
            "ISS Level 3", "ISS Level 4", "ISS Level 5", "ISS Level 6", "ISS Level 7",
            "ISS Level 8", "ISS Level 9", "ISS Level 10"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_station_list);
        setContentView(R.layout.activity_station_list);

        rvStationList = (RecyclerView) findViewById(R.id.station_list);
        rvStationList.setHasFixedSize(false);

        stationListManager = new LinearLayoutManager(this);
        rvStationList.setLayoutManager(stationListManager);

        stationListAdapter = new StationListAdapter(stationList,false);
        rvStationList.setAdapter(stationListAdapter);

        tvEmptyStationList = findViewById(R.id.tv_empty_station_list);
        tvEmptyStationList.setVisibility(stationListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);

        // For Participant Mode, fabAddTrail and Menu-Edit(Not implemented yet) should be invisible
        fabAddStation = findViewById(R.id.fab_add_station);
        // fabAddTrail.setVisibility(View.GONE); if user is participant
        fabAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StationListActivity.this, AddNewStationActivity.class));
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
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(this, SignInActivity.class));
//            finish();

        } else if (i == R.id.action_edit){
//            trailListAdapter.setEditable();
//            trailListAdapter.notifyDataSetChanged();
            stationListAdapter = new StationListAdapter(stationList, true);
            rvStationList.setAdapter(stationListAdapter);

            // How to exit Edit Mode? (not implemented yet)
        }
        return true;
    }

}
