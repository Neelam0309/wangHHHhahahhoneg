package com.example.wangzuxiu.traildemo.Activity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Trail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    String et_trail_date,name;
    String uid=getUid();
    Calendar selectedDate = Calendar.getInstance();
    private String trailName=null,trailDate=null,timestamp=null;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Add new learning trail");
        setContentView(R.layout.activity_add_new_trail);

        // Could use DatePicker to choose the date
        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);

        tv_name=findViewById(R.id.et_trail_name);
        tv_date=findViewById(R.id.et_trail_date);

        if(flag==0){  //add new trail

   /*         date=new Date(System.currentTimeMillis());
            et_trail_date=formatter.format(date); //timestamp
            tv_date.setText(formatter_date.format(selectedDate.getTime()));*/
        }

        else if (flag==1){  //edit a trail
            trailName=intent.getStringExtra("trailName");
            trailDate=intent.getStringExtra("trailDate");
            timestamp=intent.getStringExtra("timestamp");
            et_trail_date=trailDate;

            tv_name.setText(trailName);
            tv_date.setText(trailDate);
        }



        mDatabase = FirebaseDatabase.getInstance().getReference();


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

                    save(flag);
                }

                finish();
            }
        });
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void save(int flag){

        name=tv_name.getText().toString();
        //tv_id.setText(trailId);

        String trailId=tv_date.getText().toString()+"-"+name;

        if (flag==0){  //need to generate a new key

            String key=mDatabase.child("trails").push().getKey();
            Trail trail=new Trail(name,tv_date.getText().toString(),et_trail_date,trailId,key);

            Map<String,Object> childUpdates=new HashMap<>();
            Map<String,Object> trailList=trail.toMap();
            childUpdates.put("/trails/"+key,trailList);
            childUpdates.put("/trainer-trails/"+uid+"/"+key,trailList);

            mDatabase.updateChildren(childUpdates);
        }
        if(flag==1){
            //String key=getIntent().getStringExtra("trailId");
            String key=getIntent().getStringExtra("key");
            Trail trail=new Trail(name,tv_date.getText().toString(),et_trail_date,trailId,key);
            Map<String,Object> childUpdates=new HashMap<>();
            Map<String,Object> trailList=trail.toMap();
            childUpdates.put("/trails/"+ key,trailList);
            childUpdates.put("/trainer-trails/"+uid+"/"+ key,trailList);
            mDatabase.updateChildren(childUpdates);

        }

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
