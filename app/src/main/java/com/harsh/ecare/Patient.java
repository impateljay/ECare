package com.harsh.ecare;

import java.util.ArrayList;

/**
 * Created by Jay on 19-02-2017.
 */

public class Patient {

    private String Name;
    private int Age;
    private String Address;
    private String MobileNumber;
    private String emailId;

    public Patient(String name, int age, String address, String mobileNumber, String emailId) {
        Name = name;
        Age = age;
        Address = address;
        MobileNumber = mobileNumber;
        this.emailId = emailId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public static ArrayList<Patient> createContactsList(int numContacts) {
        ArrayList<Patient> contacts = new ArrayList<Patient>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Patient("Patient Name", 50, "Mumbai", "1234567890", "abc@xyz.com"));
        }

        return contacts;
    }
}
