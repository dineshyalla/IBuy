package com.example.dineshyalla.ibuy.model;

import java.util.ArrayList;
import java.util.List;

public class Payment {
    String cardHolderName;
    int CardNumber;
    int amountPaid;
    String qrcode;

    List<Order> Products_Purchased = new ArrayList<>();

    public Payment(String cardHolderName, int cardNumber, int amountPaid, String qrcode, List<Order> products_Purchased) {
        this.cardHolderName = cardHolderName;
        CardNumber = cardNumber;
        this.amountPaid = amountPaid;
        this.qrcode = qrcode;
        Products_Purchased = products_Purchased;
    }
}
