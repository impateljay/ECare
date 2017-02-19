package com.harsh.ecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    ArrayList<Appointment> contacts;
    private Button takeAppointment;
    private DatabaseReference mDatabase;
    private AppointmentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        takeAppointment = (Button) findViewById(R.id.button5);
        takeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TakeAppointmentActivity.class);
                startActivity(intent);
            }
        });

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.appointmentList);
        // Initialize contacts
        contacts = new ArrayList<Appointment>();
        // Create adapter passing in the sample user data
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference("appointment");
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Appointment user = dataSnapshot.getValue(Appointment.class);
                    if (user != null) {
                        contacts.add(user);
                        Toast.makeText(getApplicationContext(), "Date: " + user.getDate() + ", Time:" + user.getTime() + ", Doctor:" + user.getDoctor() + ", Patient:" + user.getPatient(), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
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

        adapter = new AppointmentsAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }
}
