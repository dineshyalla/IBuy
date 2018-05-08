package com.example.dineshyalla.ibuy.model;

public class Order {
    int productid;
    String productName;
    int quantity;

    public Order(int productid, String productName, int quantity) {
        this.productid = productid;
        this.productName = productName;
        this.quantity = quantity;
    }
}
