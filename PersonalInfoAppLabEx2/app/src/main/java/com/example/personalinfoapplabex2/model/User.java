package com.example.personalinfoapplabex2.model;

import java.util.ArrayList;

public class User {
    String name,dob,address,email,phno;
    enum Gender{
        male,female;
    }

    Gender gender=Gender.male;  //by default
    ArrayList<String> languagesKnown=new ArrayList<>();

    public User(String name, String dob, String address, String email, String phno) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.phno = phno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getLanguagesKnown() {
        return languagesKnown;
    }

    public void setLanguagesKnown(ArrayList<String> languagesKnown) {
        this.languagesKnown = languagesKnown;
    }
}
