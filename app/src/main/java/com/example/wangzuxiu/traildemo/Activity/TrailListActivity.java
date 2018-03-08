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

import com.example.wangzuxiu.traildemo.Adapter.TrailListAdapter;
import com.example.wangzuxiu.traildemo.R;

/**
 * Created by wangzuxiu on 04/03/18.
 */

public class TrailListActivity extends AppCompatActivity{

    private RecyclerView rvTrailList;
    private RecyclerView.Adapter trailListAdapter;
    private RecyclerView.LayoutManager trailListManager;
    private FloatingActionButton fabAddTrail;
    private TextView tvEmptyTrailList;
    private String[][] trailList = {{"Tour to ISS", "20180301-ISS", "2018-03-01"}, {"Tour to NUS", "20180401-NUS", "2018-04-01"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_trail_list);
        setContentView(R.layout.activity_trail_list);

        rvTrailList = (RecyclerView) findViewById(R.id.trail_list);
        rvTrailList.setHasFixedSize(true);

        trailListManager = new LinearLayoutManager(this);
        rvTrailList.setLayoutManager(trailListManager);

        trailListAdapter = new TrailListAdapter(trailList,false);
        rvTrailList.setAdapter(trailListAdapter);
        tvEmptyTrailList = findViewById(R.id.tv_empty_trail_list);
        // For Participant Mode, text of tvEmptyTrailList should be changed
        // tvEmptyTrailList.setText(R.string.empty_trail_list_participant); if user is participant
        tvEmptyTrailList.setVisibility(trailListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);

        // For Participant Mode, fabAddTrail and Menu-Edit(Not implemented yet) should be invisible
        fabAddTrail = findViewById(R.id.fab_add_trail);
        // fabAddTrail.setVisibility(View.GONE); if user is participant
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
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(this, SignInActivity.class));
//            finish();

        } else if (i == R.id.action_edit){
//            trailListAdapter.setEditable();
//            trailListAdapter.notifyDataSetChanged();
            trailListAdapter = new TrailListAdapter(trailList, true);
            rvTrailList.setAdapter(trailListAdapter);

            // How to exit Edit Mode? (not implemented yet)
        }
        return true;
    }

}
