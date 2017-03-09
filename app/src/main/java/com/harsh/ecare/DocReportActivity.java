package com.harsh.ecare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class DocReportActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private Button pickDoc;
    private Button pickPhoto;
    private ArrayList<String> filePaths;
    private ArrayList<String> photoPaths;
    private ArrayList<String> docPaths;
    private StorageReference reportsRef;
    private String patientName;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_report);

        patientName = getIntent().getStringExtra("patientName");
        setTitle(patientName);

        pickDoc = (Button) findViewById(R.id.pick_doc);
        pickDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(filePaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickDocument(DocReportActivity.this);
            }
        });

        pickPhoto = (Button) findViewById(R.id.pick_photo);
        pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance().setMaxCount(5)
                        .setSelectedFiles(filePaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickPhoto(DocReportActivity.this);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("reports");
        storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        // Create a child reference
        // imagesRef now points to "images"
        reportsRef = storageRef.child("reports");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS));
                    for (int i = 0; i < photoPaths.size(); i++) {
                        Uri file = Uri.fromFile(new File(photoPaths.get(i)));
                        final String fileName = file.getLastPathSegment();
                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        String restoredText = prefs.getString("loggedinUserName", null);
                        StorageReference riversRef = reportsRef.child(patientName).child(file.getLastPathSegment());
                        UploadTask uploadTask = riversRef.putFile(file);

                        // Register observers to listen for when the download is done or if it fails
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Toast.makeText(DocReportActivity.this, fileName + " uploaded failed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(DocReportActivity.this, fileName + " uploaded sucessfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                try {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        docPaths = new ArrayList<>();
                        docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                        for (int i = 0; i < docPaths.size(); i++) {
                            Uri file = Uri.fromFile(new File(docPaths.get(i)));
                            final String fileName = file.getLastPathSegment();
                            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            final String restoredText = prefs.getString("loggedinUserName", null);
                            StorageReference riversRef = reportsRef.child(patientName).child(file.getLastPathSegment());
                            UploadTask uploadTask = riversRef.putFile(file);

                            // Register observers to listen for when the download is done or if it fails
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Toast.makeText(DocReportActivity.this, fileName + " uploaded failed", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    String userId = mDatabase.push().getKey();
                                    Report user = new Report(fileName, downloadUrl.toString(), restoredText, patientName);
                                    mDatabase.child(userId).setValue(user);
                                    Toast.makeText(DocReportActivity.this, fileName + " uploaded sucessfully\n" + downloadUrl, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}