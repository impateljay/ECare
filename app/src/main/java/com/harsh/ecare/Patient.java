package com.harsh.ecare;

import java.util.ArrayList;

/**
 * Created by Jay on 19-02-2017.
 */

public class Patient {

    private String Name;
    private String Address;
    private String MobileNumber;
    private String emailId;

    public Patient(String name, String address, String mobileNumber, String emailId) {
        Name = name;
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
            contacts.add(new Patient("Patient Name", "Mumbai", "1234567890", "abc@xyz.com"));
        }

        return contacts;
    }
}
