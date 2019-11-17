package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("carId")
    @Expose
    private final int id;

    @SerializedName("carRegistrationNumber")
    @Expose
    private final String registrationNumber;

    @SerializedName("carType")
    @Expose
    private final String type;

    @SerializedName("carColour")
    @Expose
    private final String colour;

    @SerializedName("carName")
    @Expose
    private final String name;

    @SerializedName("carUser")
    @Expose
    private final User user;

    @SerializedName("carLocation")
    @Expose
    private final Location location;

    @SerializedName("createdAt")
    @Expose
    private final String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private final String updatedAt;

    public Car(final int id, final String registrationNumber, final String type,
               final String colour, final String name, final User user, final Location location,
               final String createdAt, final String updatedAt) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.colour = colour;
        this.name = name;
        this.user = user;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
