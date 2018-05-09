package com.example.dineshyalla.ibuy.service;

import com.example.dineshyalla.ibuy.model.Aisle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AisleClient {

    @GET("getProductAilse/{productname}")
    Call<Aisle> getAisle(@Path("productname") String productname);
}
