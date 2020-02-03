package com.example.blindapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    String url="https://192.168.167.30:5000/";


    @GET("marvel")
    Call<List<Hero>> getHeroes();

    @GET("video_feed")
    Call<List<Byte>> getByte();
}
