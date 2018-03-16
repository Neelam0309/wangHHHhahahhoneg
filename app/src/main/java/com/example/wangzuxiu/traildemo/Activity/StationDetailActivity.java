package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wangzuxiu.traildemo.Adapter.TabPagerAdapter;
import com.example.wangzuxiu.traildemo.R;

public class StationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        String trailKey = intent.getStringExtra("trailKey");
        this.setTitle(stationName);
        setContentView(R.layout.activity_station_detail);

        String stationId=intent.getStringExtra("stationId");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), 3 ,trailKey,stationId);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(this, SignInActivity.class));
//            finish();

        }
        return true;
    }
}
