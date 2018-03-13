package com.example.wangzuxiu.traildemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wangzuxiu.traildemo.R;

public class AddNewStationActivity extends AppCompatActivity {
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_new_station);
        setContentView(R.layout.activity_add_new_station);


        // Use MapView mv_station_location to display the map

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new station
                // ...

                finish();
            }
        });
    }
}
