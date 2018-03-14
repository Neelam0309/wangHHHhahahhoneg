package com.example.wangzuxiu.traildemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by wangzuxiu on 04/03/18.
 */

public class SelectModeActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private TextView tv;
    private Spinner spinner;
    private Button button;
    private String select;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String [] mode={"Trainer","Participant"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmode);

        button=findViewById(R.id.ok);
        button.setOnClickListener(this);

        spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,R.layout.simple_spinner_item,mode);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                select=((TextView)arg1).getText().toString();


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        tv=findViewById(R.id.user);
        tv.setText("Welcome, "+user.getDisplayName());
        Log.i("tag",user.getUid());



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.logout:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        // Firebase sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(SelectModeActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(SelectModeActivity.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        Log.i("tag","jeiwog");

        Log.i("tag",select);
        if(select=="Trainer"){
            Intent i=new Intent(SelectModeActivity.this,TrailListActivity.class);
            startActivity(i);
        }
        else if(select=="Participant"){
            startActivity(new Intent(SelectModeActivity.this,ParticipantTrailActivity.class));
        }
    }

}
