package com.example.dineshyalla.ibuy.model;

public class User {

    String customerName;
    String email;
    String password;
    String phone;
    String result;

    public User(String customerName, String email, String password, String phone) {
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getResult() {
        return result;
    }
}
