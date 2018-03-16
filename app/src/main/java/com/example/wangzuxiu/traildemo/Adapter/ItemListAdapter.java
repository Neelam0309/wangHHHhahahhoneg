package com.example.wangzuxiu.traildemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.ContributedItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mia on 05/03/18.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private ArrayList<ContributedItem> myDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // just static data now
        private ImageView ivFile;
        private TextView tvParticipantName;
        private TextView tvDescription;
        private TextView tvCreatedDate;

        private String ContributedItem_Type;
        private String ContributedItem_FirebaseURL;

        private ViewHolder(View v) {
            super(v);
            ivFile = (ImageView) v.findViewById(R.id.iv_file);
            tvParticipantName = (TextView) v.findViewById(R.id.tv_participant_name);
            tvDescription = (TextView) v.findViewById(R.id.tv_description);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context context = v.getContext();

                    String tempFileName = "tempFile";

                    final File localFile = new File(context.getExternalCacheDir(), tempFileName + "." + ContributedItem_Type);
                    System.out.println(localFile.getAbsolutePath());

                    // Download the file
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl(ContributedItem_FirebaseURL);

                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // View relevant file

                            String ArgFileType;
                            if (ContributedItem_Type.equals("image")){
                                ArgFileType = "image/*";
                            } else if (ContributedItem_Type.equals("audio")){
                                ArgFileType = "audio/*";
                            } else if (ContributedItem_Type.equals("pdf")){
                                ArgFileType = "application/pdf";
                            } else if (ContributedItem_Type.equals("doc")){
                                ArgFileType = "application/msword";
                            } else{
                                ArgFileType = "image/*";
                            }

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(localFile), ArgFileType);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            context.startActivity(intent);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                        }
                    });




                }
            });
        }


    }

    public ItemListAdapter(ArrayList<ContributedItem> ContributedItemList, Context context) {
        myDataSet = ContributedItemList;
        this.context = context;
    }

    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_list, parent, false);
        return new ItemListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemListAdapter.ViewHolder viewHolder, int position) {
        // ivFile set Thumbnail for each file according to its file type (not implemented yet)
        viewHolder.tvDescription.setText(myDataSet.get(position).getDescription());
        viewHolder.tvParticipantName.setText(myDataSet.get(position).getUserName());
        viewHolder.tvCreatedDate.setText(myDataSet.get(position).getTimeCreation());
        if (myDataSet.get(position).getFileType().equals("image")) {
            String fileURL = myDataSet.get(position).getFileURL();
            String fileURL_split[]= fileURL.split("/");
            String fileKeyAndAuth = fileURL_split[fileURL_split.length - 1];

            String fileKeyAndAuth_split[]= fileKeyAndAuth.split("\\?");
            String fileKey = fileKeyAndAuth_split[0];

            final File localFile = new File(this.context.getExternalCacheDir(), fileKey + ".jpg");

            // Download the file
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(myDataSet.get(position).getFileURL());

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Successfully downloaded data to local file
                    // View relevant file
                    Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    viewHolder.ivFile.setImageBitmap(myBitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    viewHolder.ivFile.setImageResource(R.mipmap.ic_launcher);
                }
            });
        } else if (myDataSet.get(position).getFileType().equals("audio")) {
            viewHolder.ivFile.setImageResource(R.mipmap.audio);
        } else if (myDataSet.get(position).getFileType().equals("pdf")) {
            viewHolder.ivFile.setImageResource(R.mipmap.pdf);
        } else if (myDataSet.get(position).getFileType().equals("doc")) {
            viewHolder.ivFile.setImageResource(R.mipmap.doc);
        } else {
            viewHolder.ivFile.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolder.ContributedItem_Type = myDataSet.get(position).getFileType();
        viewHolder.ContributedItem_FirebaseURL = myDataSet.get(position).getFileURL();
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }
}