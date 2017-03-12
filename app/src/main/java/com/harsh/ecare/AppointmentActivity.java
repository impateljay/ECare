package com.harsh.ecare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class AppointmentActivity extends AppCompatActivity {

    private ArrayList<Appointment> contacts = new ArrayList<>();
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
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("loggedinUserName", null);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("appointment");
            Query query = mDatabase.orderByChild("patient").equalTo(restoredText.toString());
            query.addValueEventListener(new ValueEventListener() {
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

        adapter = new AppointmentsAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        rvContacts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        rvContacts.addOnItemTouchListener(new RecyclerTouchListener(this, rvContacts, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                final Appointment movie = contacts.get(position);
                AlertDialog alertDialog = new AlertDialog.Builder(
                        AppointmentActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("E-Care");

                // Setting Dialog Message
                alertDialog.setMessage("Do you want to delete appointment!");

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed

                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                Map<String, Object> a = (Map<String, Object>) dataSnapshot.getValue();
                                for (Map.Entry<String, Object> entry : a.entrySet()) {
                                    //Get user map
                                    Map singleUser = (Map) entry.getValue();
                                    //Get phone field and append to list
                                    String date = (String) singleUser.get("date");
                                    String time = (String) singleUser.get("time");
                                    String doctor = (String) singleUser.get("doctor");
                                    String patient = (String) singleUser.get("patient");
                                    if (date.equals(movie.getDate()) && patient.equals(movie.getPatient()) && doctor.equals(movie.getDoctor()) && time.equals(movie.getTime())) {
                                        mDatabase.child(entry.getKey()).removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), databaseError.toException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        }));
    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        if (users != null && !users.isEmpty()) {
            contacts.clear();
            contacts.add(new Appointment("<b>Date</b>", "<b>Time</b>", "<b>Doctor Name</b>", "<b>Patient Name</b>"));
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
