package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Station;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddNewStationActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText et_stationName;
    private EditText et_stationLocation;
    private EditText et_instruction;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_new_station);
        setContentView(R.layout.activity_add_new_station);

        mDatabase = FirebaseDatabase.getInstance().getReference("/stations");
        // Use MapView mv_station_location to display the map

        et_stationName = (EditText)findViewById(R.id.et_station_name) ;
        et_stationLocation = (EditText)findViewById(R.id.tv_station_address) ;
        et_instruction = (EditText) findViewById(R.id.et_station_instruction);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new station
                // ...
                if(isValid()){

                    save();
                }

                finish();
            }
        });
    }

    public boolean isValid(){
        boolean isValid=true;
        if(TextUtils.isEmpty(et_stationName.getText().toString().trim())){
            et_stationName.setError("please fill in the name");
            isValid=false;
        }
        if(TextUtils.isEmpty(et_stationLocation.getText().toString().trim())){
            et_stationLocation.setError("please select a date");
            isValid=false;
        }
        if(TextUtils.isEmpty(et_instruction.getText().toString().trim())){
            et_instruction.setError("please select a date");
            isValid=false;
        }
        return isValid;
    }

    public void save()
    {


        String key = getTrailKey();
        String stationKey = mDatabase.child(key).push().getKey();
        System.out.println("station key"+stationKey);
        Station station = new Station(et_stationName.getText().toString(),et_stationLocation.getText().toString(),et_instruction.getText().toString(),stationKey);
        mDatabase.child(key).child(stationKey).setValue(station);
        /*Map<String,Object> childUpdates=new HashMap<>();
        Map<String,Object> trailList=station.toMap();
        childUpdates.put("/stations/"+trailID,trailList);*/
        //mDatabase.updateChildren(childUpdates);
    }

    public String getTrailKey() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        return key;
    }


}
