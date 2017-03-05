package com.harsh.ecare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class DocPrescriptionActivity extends AppCompatActivity {

    private EditText prescriptionEditText;
    private Button sendButton;
    private DatabaseReference mDatabase;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prescription);

        prescriptionEditText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.button8);

        s = getIntent().getStringExtra("patientName");
        setTitle(s);
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
    }

    private void createPrescreption(String prescription) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("loggedinUserName", null);
        String userId = mDatabase.push().getKey();
        Prescription user = new Prescription(restoredText, s, prescription, DateFormat.getDateTimeInstance().format(new Date()));
        mDatabase.child(userId).setValue(user);
    }
}
