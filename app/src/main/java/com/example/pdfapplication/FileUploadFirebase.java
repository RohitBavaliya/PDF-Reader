package com.example.pdfapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class FileUploadFirebase extends AppCompatActivity {
    ImageView uploadImage, cancelImage, fileImage, browseImage;
    EditText fileTitle;

    Uri filePath;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload_firebase);

        uploadImage = (ImageView) findViewById(R.id.upload_image);
        cancelImage = (ImageView) findViewById(R.id.cancel_image);
        fileImage = (ImageView) findViewById(R.id.file_image);
        browseImage = (ImageView) findViewById(R.id.browse_image);

        fileTitle = (EditText) findViewById(R.id.file_title);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("documents");

        fileImage.setVisibility(View.INVISIBLE);
        cancelImage.setVisibility(View.INVISIBLE);


        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelImage.setVisibility(View.INVISIBLE);
                fileImage.setVisibility(View.INVISIBLE);
                browseImage.setVisibility(View.VISIBLE);
            }
        });


        browseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProcess(filePath);
            }
        });
    }

    private void uploadProcess(Uri filePath) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploading....!!");
        dialog.show();

        final StorageReference reference = storageReference.child("upload/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Model model = new Model(0,fileTitle.getText().toString(),uri.toString(),0,0);
                                databaseReference.child(databaseReference.push().getKey()).setValue(model);
                                dialog.dismiss();
                                Toast.makeText(FileUploadFirebase.this, "File Uploaded..", Toast.LENGTH_SHORT).show();

                                fileImage.setVisibility(View.INVISIBLE);
                                cancelImage.setVisibility(View.INVISIBLE);
                                browseImage.setVisibility(View.VISIBLE);

                                fileTitle.setText("");

                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded: "+percent+"%");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 & resultCode==RESULT_OK)
        {
            filePath = data.getData();
            fileImage.setVisibility(View.VISIBLE);
            cancelImage.setVisibility(View.VISIBLE);
            browseImage.setVisibility(View.INVISIBLE);

        }
    }
}