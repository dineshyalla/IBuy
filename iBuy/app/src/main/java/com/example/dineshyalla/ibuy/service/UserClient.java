package com.example.dineshyalla.ibuy.service;

import com.example.dineshyalla.ibuy.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("signup")
    Call<User> createAccount(@Body User user);
}
