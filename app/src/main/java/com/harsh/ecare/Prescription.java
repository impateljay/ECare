package com.harsh.ecare;

/**
 * Created by HarsH on 05-03-2017.
 */

public class Prescription {
    private String DoctorName;
    private String PatientName;
    private String Prescription;
    private String DateTime;

    public Prescription() {
    }

    public Prescription(String doctorName, String patientName, String prescription, String dateTime) {
        DoctorName = doctorName;
        PatientName = patientName;
        Prescription = prescription;
        DateTime = dateTime;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
