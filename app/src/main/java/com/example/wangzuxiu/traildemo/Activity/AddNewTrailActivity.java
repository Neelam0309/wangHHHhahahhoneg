package com.example.wangzuxiu.traildemo.Activity;



import com.example.wangzuxiu.traildemo.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.model.Trail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddNewTrailActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText tv_name,tv_date;
    private DatabaseReference mDatabase;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd hh:mm:ss");
    private SimpleDateFormat formatter_date=new SimpleDateFormat("yyyMMdd");
    private Date date;
    String uid,et_trail_date,trailId,name;
    Calendar selectedDate = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("new_trail");
        setContentView(R.layout.activity_add_new_trail);

        // Could use DatePicker to choose the date

        // Learning Trail ID should align with the format ‘YYYYMMDD-<Trail Code>'
        // Trail Code should be one word or restricted to X letters?(eg. 3)
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tv_name=findViewById(R.id.et_trail_name);
        tv_date=findViewById(R.id.et_trail_date);
        //tv_id=findViewById(R.id.et_trail_id);

        date=new Date(System.currentTimeMillis());
        et_trail_date=formatter.format(date); //timestamp
        uid=getUid();

        tv_date.setText(formatter_date.format(selectedDate.getTime()));
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                tv_date.setText(formatter_date.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddNewTrailActivity.this, onDateSetListener,
                                selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                                selectedDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new trail
                if(isValid()){

                    save();
                }


                finish();
            }
        });
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void save(){

        name=tv_name.getText().toString();
        trailId=et_trail_date+"-"+name;
        //tv_id.setText(trailId);

        Trail trail=new Trail(name,tv_date.getText().toString(),et_trail_date);
        String key=trailId;
//        mDatabase.child(key).child("userId").setValue(trail.userId);
//        mDatabase.child(key).child("trailName").setValue(trail.trailName);
//        mDatabase.child(key).child("trailDate").setValue(trail.trailDate);
//        mDatabase.child(key).child("timestamp").setValue(trail.timestamp);

        Map<String,Object> childUpdates=new HashMap<>();
        Map<String,Object> trailList=trail.toMap();
        childUpdates.put("/trails/"+key,trailList);
        childUpdates.put("/trainer-trails/"+uid+"/"+key,trailList);

        mDatabase.updateChildren(childUpdates);

    }
    public boolean isValid(){
        boolean isValid=true;
        if(TextUtils.isEmpty(tv_name.getText().toString().trim())){
            tv_name.setError("please fill in the name");
            isValid=false;
        }
        if(TextUtils.isEmpty(tv_date.getText().toString().trim())){
            tv_date.setError("please select a date");
            isValid=false;
        }
        return isValid;
    }
}
