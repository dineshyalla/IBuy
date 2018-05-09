package com.example.dineshyalla.ibuy.model;

public class UserLogin {

    String customerName;
    String password;
    String result;

    public UserLogin(String customerName, String password) {
        this.customerName = customerName;
        this.password = password;
    }

    public String getResult() {
        return result;
    }
}
