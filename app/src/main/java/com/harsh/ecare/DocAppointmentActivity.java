package com.harsh.ecare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DocAppointmentActivity extends AppCompatActivity {

    ArrayList<Appointment> contacts = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DocAppointmentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appointment);

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.appointmentList);
        // Initialize contacts
//        contacts = Appointment.createContactsList(20);


        try {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("appointment");
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot != null || dataSnapshot.getChildrenCount() > 0) {
                        collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                    }

//                    Appointment user = dataSnapshot.getValue(Appointment.class);
//                    if(user != null){
//                        contacts.add(user);
//                        Toast.makeText(getApplicationContext(), "Date: " + user.getDate() + ", Time:" + user.getTime() + ", Doctor:" + user.getDoctor() + ", Patient:" + user.getPatient(),Toast.LENGTH_LONG).show();
//                        adapter.notifyDataSetChanged();
//                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(), "Failed to read value." + databaseError.toException(), Toast.LENGTH_LONG).show();
                }
            });
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    ArrayList<Appointment> td = (ArrayList<Appointment>) dataSnapshot.getValue();
//                    if(td!=null){
//                        contacts.addAll(td);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        } catch (Exception ex) {

        }

        // Create adapter passing in the sample user data
        adapter = new DocAppointmentsAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        if (users != null && !users.isEmpty()) {
            //iterate through each user, ignoring their UID
            for (Map.Entry<String, Object> entry : users.entrySet()) {
                //Get user map
                Map singleUser = (Map) entry.getValue();
                //Get phone field and append to list
                String date = (String) singleUser.get("date");
                String time = (String) singleUser.get("time");
                String doctor = (String) singleUser.get("doctor");
                String patient = (String) singleUser.get("patient");
                contacts.add(new Appointment(date, time, doctor, patient));
//            Toast.makeText(getApplicationContext(), "Date: " + date + ", Time:" + time + ", Doctor:" + doctor + ", Patient:" + patient, Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
