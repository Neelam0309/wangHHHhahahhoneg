package com.example.wangzuxiu.traildemo.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.ContributedItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class AddNewItemActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnChooseFile;

    private Uri fileUri;
    private String fileName;
    private String fileType;
    private String uid= FirebaseAuth.getInstance().getUid();
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        // StrictMode.setVmPolicy(builder.build());

        this.setTitle(R.string.title_new_item);
        setContentView(R.layout.activity_add_new_item);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        btnChooseFile = (Button) findViewById(R.id.btn_choose_file);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                String[] mimetypes = {"image/*", "audio/*", "application/pdf", "application/msword"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1001);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    System.out.println("No file manager");
                }
            }
        });


        //////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("items");

        mStorageRef = FirebaseStorage.getInstance().getReference();
        //////////

        // modify this according to context
        final String userID = uid;
        final String stationID = "-L7XDL5ditoY4_Dr0BWG";
        final String userName = "Bob";
        final String timeCreation = "2018-03-13";


        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ContributedItem ci = new ContributedItem();
                ci.setUserId(userID);
                ci.setUserName(userName);
                ci.setTimeCreation(timeCreation);
                ci.setFileType(AddNewItemActivity.this.fileType);

                EditText tvDescription = (EditText) findViewById(R.id.et_description);
                ci.setDescription(tvDescription.getText().toString());

                ci.setFileURL("NA");

                DatabaseReference trailStationRef = ref.child(stationID);
                DatabaseReference newContributedItem = trailStationRef.push();
                newContributedItem.setValue(ci);

                final String ci_Firebase_key = newContributedItem.getKey();

                // upload file to Firebase Cloud
                Uri local_uri = AddNewItemActivity.this.fileUri;
                StorageReference catRef = mStorageRef.child(ci_Firebase_key);
                catRef.putFile(local_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri firebase_uri = taskSnapshot.getDownloadUrl();

                        ci.setFileURL(firebase_uri.toString());

                        String FirebaseLocation = "items/" + stationID + "/" + ci_Firebase_key + "/fileURL";
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference(FirebaseLocation);
                        ref.setValue(firebase_uri.toString());

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    this.fileUri = uri;

                    File selectedFile = new File(uri.getPath());
                    this.fileName = selectedFile.getName();

                    TextView textView = (TextView) findViewById(R.id.tv_file_name);
                    textView.setText(fileName);

                    String filenameArray[] = fileName.split("\\.");
                    String extension = filenameArray[filenameArray.length - 1];

                    if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                        this.fileType = "image";
                    } else if (extension.equals("mp3") || extension.equals("wav")) {
                        this.fileType = "audio";
                    } else if (extension.equals("pdf")) {
                        this.fileType = "pdf";
                    } else if (extension.equals("docx") || extension.equals("word")) {
                        this.fileType = "doc";
                    } else {
                        this.fileType = "other";
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

