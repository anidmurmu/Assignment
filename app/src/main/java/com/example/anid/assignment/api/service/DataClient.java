package com.example.anid.assignment.api.service;

import com.example.anid.assignment.api.model.Data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataClient {

    //https://api.myjson.com/bins/chou4

    @GET("bins/chou4")
    Call<Data> getData();
}
