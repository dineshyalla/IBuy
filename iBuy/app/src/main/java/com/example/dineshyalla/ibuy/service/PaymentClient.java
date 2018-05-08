package com.example.dineshyalla.ibuy.service;

import com.example.dineshyalla.ibuy.model.Payment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentClient {
    @POST("pay")
    Call<Payment> createPayment(@Body Payment payment);
}
