package com.harsh.ecare;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.harsh.ecare.SignupActivity.MY_PREFS_NAME;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText name;
    private EditText address;
    private EditText email;
    private EditText mobile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.input_name);
        address = (EditText) findViewById(R.id.input_address);
        email = (EditText) findViewById(R.id.input_email);
        mobile = (EditText) findViewById(R.id.input_mobile);


        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("loggedinUserEmail", null);

        if (restoredText.equals("doc@gmail.com")) {
            address.setText("Mumbai");
            email.setText(restoredText);
            mobile.setText("9685741023");
            name.setText("Doctor 1");
            progressDialog.hide();
        } else if (restoredText.equals("doctor@gmail.com")) {
            address.setText("Pune");
            email.setText(restoredText);
            mobile.setText("7896543210");
            name.setText("Doctor 2");
            progressDialog.hide();
        } else if (restoredText.equals("caregiver@gmail.com")) {
            address.setText("Banglore");
            email.setText(restoredText);
            mobile.setText("8596743201");
            name.setText("Doctor 3");
            progressDialog.hide();
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("patients");
            Query query = mDatabase.orderByChild("emailId").equalTo(restoredText);
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
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), "Failed to read value." + databaseError.toException(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        if (users != null && !users.isEmpty()) {
            for (Map.Entry<String, Object> entry : users.entrySet()) {
                //Get user map
                Map singleUser = (Map) entry.getValue();
                //Get phone field and append to list
                address.setText((String) singleUser.get("address"));
                email.setText((String) singleUser.get("emailId"));
                mobile.setText((String) singleUser.get("mobileNumber"));
                name.setText((String) singleUser.get("name"));
            }
        }
        progressDialog.hide();
    }
}
