package com.example.wangzuxiu.traildemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddNewTrailActivity extends AppCompatActivity {
    private Button btnSave;
    private TextView tv_name,tv_date;
    private DatabaseReference mDatabase;
    private String name,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_new_trail);
        setContentView(R.layout.activity_add_new_trail);

        // Could use DatePicker to choose the date

        // Learning Trail ID should align with the format ‘YYYYMMDD-<Trail Code>'
        // Trail Code should be one word or restricted to X letters?(eg. 3)
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tv_name=findViewById(R.id.et_trail_name);
        tv_date=findViewById(R.id.et_trail_date);

        name=tv_name.getText().toString();
        date=tv_date.getText().toString();

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new trail
                // ...
                finish();
            }
        });
    }
}
