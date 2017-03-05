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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class PatientsListActivity extends AppCompatActivity {

    private ArrayList<Patient> contacts = new ArrayList<>();
    private DatabaseReference mDatabase;
    private PatientsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.patientsList);

        try {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String restoredText = prefs.getString("loggedinUserName", null);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("appointment");
            Query query = mDatabase.orderByChild("doctor").equalTo(restoredText.toString());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot != null || dataSnapshot.getChildrenCount() > 0) {
                        collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Failed to read value." + databaseError.toException(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
        }
        adapter = new PatientsListAdapter(this, contacts);
        rvContacts.setAdapter(adapter);
        rvContacts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
//        rvContacts.addOnItemTouchListener(new PatientsListAdapter.RecyclerTouchListener(this, rvContacts, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Patient movie = contacts.get(position);
//                Toast.makeText(getApplicationContext(), view.getId() + " is selected!", Toast.LENGTH_SHORT).show();
//                int a = view.getId();
//                if(view.getId() == R.id.btn_prescription) {
//                    Intent intent = new Intent(view.getContext(), DocPrescriptionActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//            }
//        }));
    }


    private void collectPhoneNumbers(Map<String, Object> users) {
        if (users != null && !users.isEmpty()) {
            contacts.clear();
            List<String> patientsName = new ArrayList<>();
            for (Map.Entry<String, Object> entry : users.entrySet()) {
                Map singleUser = (Map) entry.getValue();
                String patient = (String) singleUser.get("patient");
                patientsName.add(patient);
            }
            Set<String> uniqueGas = new HashSet<String>(patientsName);
            contacts.clear();
            for (String value : uniqueGas) {
                contacts.add(new Patient(value, null, null, null));
                adapter.notifyDataSetChanged();
            }
        }
    }
}