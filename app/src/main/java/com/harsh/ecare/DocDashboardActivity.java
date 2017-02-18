package com.harsh.ecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DocDashboardActivity extends AppCompatActivity {

    private Button appointmentsList;
    private Button patientsList;
    private Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_dashboard);

        appointmentsList = (Button) findViewById(R.id.btnAppointmentsList);
        patientsList = (Button) findViewById(R.id.btnPatientsList);
        editProfile = (Button) findViewById(R.id.btnEditProfile);

        appointmentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DocAppointmentActivity.class);
                startActivity(intent);
            }
        });

        patientsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientsListActivity.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
