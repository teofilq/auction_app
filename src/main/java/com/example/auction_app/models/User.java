package com.example.auction_app.models;

import java.util.Date;

public class User {
    private int userID;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private Date registrationDate;
    public User() {
    }
    public User(int userID, String email, String password, String name, String address, String phone, Date registrationDate) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.registrationDate = registrationDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
