package com.example.wangzuxiu.traildemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;

/**
 * Created by wangzuxiu on 04/03/18.
 */

public class InputTrailIdActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView inputId;
    private Button addbutton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_trail_id);

        inputId=findViewById(R.id.inputID);
        addbutton=findViewById(R.id.addTrail);
        addbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String trailId=inputId.getText().toString();

    }
}
