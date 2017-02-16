package com.harsh.ecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    ArrayList<Appointment> contacts;
    private Button takeAppointment;

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
        contacts = Appointment.createContactsList(20);
        // Create adapter passing in the sample user data
        AppointmentsAdapter adapter = new AppointmentsAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }
}
