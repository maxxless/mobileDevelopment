package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("carId")
    @Expose
    private int id;

    @SerializedName("carRegistrationNumber")
    @Expose
    private String registrationNumber;

    @SerializedName("carType")
    @Expose
    private String type;

    @SerializedName("carColour")
    @Expose
    private String colour;

    @SerializedName("carName")
    @Expose
    private String name;

    @SerializedName("carUser")
    @Expose
    private User user;

    @SerializedName("carLocation")
    @Expose
    private Location location;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getType() {
        return type;
    }

    public String getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
