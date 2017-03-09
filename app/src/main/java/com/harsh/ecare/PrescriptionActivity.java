package com.harsh.ecare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class PrescriptionActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private PrescriptionAdapter adapter;
    private ArrayList<Prescription> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        mDatabase = FirebaseDatabase.getInstance().getReference("prescription");

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.prescription_recyclerview);
        // Initialize contacts
        contacts = new ArrayList<Prescription>();
        // Create adapter passing in the sample user data
        try {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("loggedinUserName", null);
            Query query = mDatabase.orderByChild("patientName").equalTo(restoredText.toString());
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

    private void collectPhoneNumbers(Map<String, Object> users) {

        if (users != null && !users.isEmpty()) {
            contacts.clear();
            //iterate through each user, ignoring their UID
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("loggedinUserName", null);
            for (Map.Entry<String, Object> entry : users.entrySet()) {
                //Get user map
                Map singleUser = (Map) entry.getValue();
                //Get phone field and append to list
                String dateTime = (String) singleUser.get("dateTime");
                String doctorName = (String) singleUser.get("doctorName");
                String patientName = (String) singleUser.get("patientName");
                String prescription = (String) singleUser.get("prescription");
                if (patientName.equals(restoredText)) {
                    contacts.add(new Prescription(doctorName, patientName, prescription, dateTime));
                }
//            Toast.makeText(getApplicationContext(), "Date: " + date + ", Time:" + time + ", Doctor:" + doctor + ", Patient:" + patient, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
