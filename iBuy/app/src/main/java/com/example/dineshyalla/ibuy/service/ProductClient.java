package com.example.dineshyalla.ibuy.service;

import com.example.dineshyalla.ibuy.model.ProductClientClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductClient {

    @GET("getProduct/{barcode}")
    Call<ProductClientClass> getProduct(@Path("barcode") String barcode);
}
