package com.harsh.ecare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.philliphsu.bottomsheetpickers.date.BottomSheetDatePickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.numberpad.NumberPadTimePickerDialog;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;

public class TakeAppointmentActivity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "MainActivity";

    private TextView mText;
    private TextView mDate;
    private String selectedDate;
    private String selectedTime;
    private Button schedule;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_appointment);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mText = (TextView) findViewById(R.id.text);
        mDate = (TextView) findViewById(R.id.date);
        schedule = (Button) findViewById(R.id.button7);

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    NumberPadTimePickerDialog dialog = NumberPadTimePickerDialog.newInstance(
                            TakeAppointmentActivity.this);
                dialog.setThemeDark(false);
                    dialog.show(getSupportFragmentManager(), TAG);
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                BottomSheetDatePickerDialog dialog = BottomSheetDatePickerDialog.newInstance(
                        TakeAppointmentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                dialog.setThemeDark(false);
                dialog.setMinDate(now);
                Calendar max = Calendar.getInstance();
                max.add(Calendar.YEAR, 10);
                dialog.setMaxDate(max);
                dialog.setYearRange(1970, 2032);
                dialog.show(getSupportFragmentManager(), TAG);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Your appointment is booked successfully", Toast.LENGTH_LONG).show();
                createAppointment("Doctor Name", selectedDate, selectedTime);
                finish();
            }
        });
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        mText.setText("Time : " + DateFormat.getTimeFormat(this).format(cal.getTime()));
        selectedTime = DateFormat.getTimeFormat(this).format(cal.getTime());
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDate.setText("Date : " + DateFormat.getDateFormat(this).format(cal.getTime()));
        selectedDate = DateFormat.getDateFormat(this).format(cal.getTime());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                findViewById(R.id.fab).requestFocus();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createAppointment(String doctorName, String date, String time) {
        SecureRandom random = new SecureRandom();
        String appointmentId = new BigInteger(130, random).toString(32);
        Appointment user = new Appointment(date, time, doctorName, "Harsh");
        mDatabase.child("appointment").child(appointmentId).setValue(user);
    }
}
