package com.harsh.ecare;

import java.util.ArrayList;

/**
 * Created by Jay on 16-02-2017.
 */

public class Appointment {

    private String date;
    private String time;
    private String doctor;

    public Appointment(String date, String time, String doctor) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
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

    private static int lastAppointmentId = 0;

    public static ArrayList<Appointment> createContactsList(int numContacts) {
        ArrayList<Appointment> contacts = new ArrayList<Appointment>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Appointment("Appointment " + ++lastAppointmentId, "15:00", "Doctor" + ++lastAppointmentId));
        }

        return contacts;
    }
}
