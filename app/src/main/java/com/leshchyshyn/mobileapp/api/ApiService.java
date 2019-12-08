package com.leshchyshyn.mobileapp.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.leshchyshyn.mobileapp.data.model.Car;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/fares")
    Call<JsonArray> getFares();

    @GET("/users")
    Call<JsonArray> getUsers();

    @GET("/cars")
    Call<JsonArray> getCars();

    @GET("/cars/{carId}")
    Call<JsonObject> getCarById(@Path("carId") String carId);

    @POST("/cars")
    Call<Car> addCar(@Body Car car);

    @GET("/locations")
    Call<JsonArray> getLocations();

    @GET("/photos")
    Call<JsonArray> getPhotos();
}
