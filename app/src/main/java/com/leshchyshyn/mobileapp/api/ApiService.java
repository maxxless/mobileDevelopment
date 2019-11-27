package com.leshchyshyn.mobileapp.api;

import com.google.gson.JsonArray;
import com.leshchyshyn.mobileapp.data.model.Car;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/fares")
    Call<JsonArray> getFares();

    @GET("/users")
    Call<JsonArray> getUsers();

    @GET("/cars")
    Call<JsonArray> getCars();

    @POST("/cars")
    Call<Car> addCar(@Body Car car);

    @GET("/locations")
    Call<JsonArray> getLocations();

    @GET("/photos")
    Call<JsonArray> getPhotos();
}
