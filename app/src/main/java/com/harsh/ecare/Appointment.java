package com.harsh.ecare;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Jay on 16-02-2017.
 */
@IgnoreExtraProperties
public class Appointment {

    private String date;
    private String time;
    private String doctor;
    private String patient;

    public Appointment() {
    }

    public Appointment(String date, String time, String doctor, String patient) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    private static int lastAppointmentId = 0;

    public static ArrayList<Appointment> createContactsList(int numContacts) {
        ArrayList<Appointment> contacts = new ArrayList<Appointment>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Appointment("19-02-2017", "15:00", "Doctor" + ++lastAppointmentId, "Patient " + lastAppointmentId));
        }

        return contacts;
    }
}
