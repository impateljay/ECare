package com.harsh.ecare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class DocPrescriptionActivity extends AppCompatActivity {

    private EditText prescriptionEditText;
    private Button sendButton;
    private DatabaseReference mDatabase;
    private String patientName;
    private PrescriptionAdapter adapter;
    private ArrayList<Prescription> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prescription);

        prescriptionEditText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.button8);

        patientName = getIntent().getStringExtra("patientName");
        setTitle(patientName);
        mDatabase = FirebaseDatabase.getInstance().getReference("prescription");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prescriptionEditText == null || prescriptionEditText.getText().toString().isEmpty() || prescriptionEditText.getText().toString() == null) {
                    Toast.makeText(view.getContext(), "Enter prescreption", Toast.LENGTH_SHORT).show();
                } else {
                    createPrescreption(prescriptionEditText.getText().toString());
                    Toast.makeText(getApplicationContext(), "Prescription Sent Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recyclerviewPrescription);
        // Initialize contacts
        contacts = new ArrayList<Prescription>();
        // Create adapter passing in the sample user data
        try {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("loggedinUserName", null);
            Query query = mDatabase.orderByChild("doctorName").equalTo(restoredText.toString());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot != null || dataSnapshot.getChildrenCount() > 0) {
                        collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(), "Failed to read value." + databaseError.toException(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
        }

        adapter = new PrescriptionAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        rvContacts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    private void createPrescreption(String prescription) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("loggedinUserName", null);
        String userId = mDatabase.push().getKey();
        Prescription user = new Prescription(restoredText, patientName, prescription, DateFormat.getDateTimeInstance().format(new Date()));
        mDatabase.child(userId).setValue(user);
    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        if (users != null && !users.isEmpty()) {
            contacts.clear();
            //iterate through each user, ignoring their UID
            for (Map.Entry<String, Object> entry : users.entrySet()) {
                //Get user map
                Map singleUser = (Map) entry.getValue();
                //Get phone field and append to list
                String dateTime = (String) singleUser.get("dateTime");
                String doctorName = (String) singleUser.get("doctorName");
                String patientName = (String) singleUser.get("patientName");
                String prescription = (String) singleUser.get("prescription");
                if (patientName.equals(this.patientName)) {
                    contacts.add(new Prescription(doctorName, patientName, prescription, dateTime));
                }
//            Toast.makeText(getApplicationContext(), "Date: " + date + ", Time:" + time + ", Doctor:" + doctor + ", Patient:" + patient, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
