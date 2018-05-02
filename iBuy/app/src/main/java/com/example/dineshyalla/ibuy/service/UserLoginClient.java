package com.example.dineshyalla.ibuy.service;

import com.example.dineshyalla.ibuy.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserLoginClient {

    @Headers("Content-Type: application/json")
    @POST("signIn")
    Call<UserLogin> loginAccount(@Body UserLogin userLogin);
}
