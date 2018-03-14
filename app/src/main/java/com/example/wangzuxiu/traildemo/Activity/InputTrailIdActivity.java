package com.example.wangzuxiu.traildemo.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.Trail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by wangzuxiu on 04/03/18.
 */

public class InputTrailIdActivity extends AppCompatActivity{

    private TextView inputId;
    private Button addbutton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_trail_id);

        inputId=findViewById(R.id.inputID);
        addbutton=findViewById(R.id.addTrail);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    String trailId=inputId.getText().toString();
                    Log.i("tag",trailId);
                    final DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                    final Context context=v.getContext();
                    Query query=ref.child("trails").orderByChild("trailId").equalTo(inputId.getText().toString());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() == 1) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    String uid = FirebaseAuth.getInstance().getUid();
                                    Trail trail = childSnapshot.getValue(Trail.class);
                                    ref.child("participant-trails").child(uid).child(key).setValue(trail.toMap());
                                    finish();

                                }
                            }
                            if (dataSnapshot.getChildrenCount() == 0) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("wrong inputId");
                                builder.setMessage("your inputId doesn't exist");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                });

                                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog dialog= builder.create();
                                dialog.show();

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

        });



    }



    public boolean isValid(){
        boolean isValid=true;
        if(TextUtils.isEmpty(inputId.getText().toString().trim())){
            inputId.setError("please fill in the name");
            isValid=false;
        }
        return isValid;
    }
}