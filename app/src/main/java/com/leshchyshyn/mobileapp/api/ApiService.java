package com.leshchyshyn.mobileapp.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/fares")
    Call<JsonArray> getFares();

    @GET("/users")
    Call<JsonArray> getUsers();

    @GET("/cars")
    Call<JsonArray> getCars();

    @GET("/locations")
    Call<JsonArray> getLocations();

    @GET("/photos")
    Call<JsonArray> getPhotos();
}
