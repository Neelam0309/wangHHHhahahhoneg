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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    int flag;
    String stationName,location,instructions;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_new_station);
        setContentView(R.layout.activity_add_new_station);
        intent= getIntent();
        flag = intent.getIntExtra("flag",0);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Use MapView mv_station_location to display the map
        et_stationName = (EditText) findViewById(R.id.et_station_name);
        et_stationLocation = (EditText) findViewById(R.id.tv_station_address);
        et_instruction = (EditText) findViewById(R.id.et_station_instruction);

        if(flag==0) {

        }
        else if(flag==1)
        {
            stationName=intent.getStringExtra("stationName");
            location = intent.getStringExtra("location");
            instructions= intent.getStringExtra("instructions");
            et_stationName.setText(stationName);
            et_stationLocation.setText(location);
            et_instruction.setText(instructions);
        }

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new station
                // ...
                if(isValid()){

                    save();
                    finish();
                }



            }
        });
    }

    public boolean isValid(){
        boolean isValid=true;
        if(TextUtils.isEmpty(et_stationName.getText().toString().trim())){
            et_stationName.setError("Please enter station name");
            isValid=false;
        }
        if(TextUtils.isEmpty(et_stationLocation.getText().toString().trim())){
            et_stationLocation.setError("Please enter location");
            isValid=false;
        }
        if(TextUtils.isEmpty(et_instruction.getText().toString().trim())){
            et_instruction.setError("Please enter instructions");
            isValid=false;
        }
        return isValid;
    }

    public void save()
    {

        if(flag==0) {
            String key = getTrailKey();
            String stationKey = mDatabase.child(key).push().getKey();
            System.out.println("station key" + stationKey);
            final Station station = new Station(et_stationName.getText().toString(), et_stationLocation.getText().toString(), et_instruction.getText().toString(), stationKey);
            System.out.println("Station................"+station.getStationName());
            mDatabase.child("stations/"+key).child(stationKey).setValue(station);


        }
        if(flag==1)
        {
            String key = getTrailKey();
            String stationKey = intent.getStringExtra("stationKey");
            final Station station = new Station(et_stationName.getText().toString(),
                    et_stationLocation.getText().toString(),et_instruction.getText().toString(),stationKey);
            System.out.println("Station................"+station.getStationName());
            mDatabase.child("stations/"+key).child(stationKey).setValue(station);
        }
    }

    public String getTrailKey() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        return key;
    }


}
