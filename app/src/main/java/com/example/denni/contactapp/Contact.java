package com.example.denni.contactapp;

import com.google.firebase.database.PropertyName;

public class Contact {

    private String key;
    private String contactName;
    private String contactEmail;
    private String contactPhone;





    public Contact(String key, String contactName , String contactEmail, String contactPhone) {
        this.key = key;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;


    }
    @PropertyName("email")
    public String getContactEmail() {

        return contactEmail;
    }

    @PropertyName("email")
    public void setContactEmail(String contactEmail) {

        this.contactEmail = contactEmail;
    }

    public Contact() {

    }

    @PropertyName("name")
    public String getContactName() {

        return contactName;
    }

    @PropertyName("name")
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    @PropertyName("phoneNumber")
    public String getContactPhone() {

        return contactPhone;
    }

    @PropertyName("phoneNumber")
    public void setContactPhone(String contactPhone) {

        this.contactPhone = contactPhone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
