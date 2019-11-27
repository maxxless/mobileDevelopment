package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("carId")
    @Expose
    private final int id;

    @SerializedName("registrationNumber")
    @Expose
    private final String registrationNumber;

    @SerializedName("type")
    @Expose
    private final String type;

    @SerializedName("colour")
    @Expose
    private final String colour;

    @SerializedName("name")
    @Expose
    private final String name;

    @SerializedName("imageUrl")
    @Expose
    private final String imageUrl;

    @SerializedName("user")
    @Expose
    private final User user;

    @SerializedName("location")
    @Expose
    private final Location location;

    @SerializedName("createdAt")
    @Expose
    private final String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private final String updatedAt;

    public Car(final int id, final String registrationNumber, final String type, String imageUrl,
               final String colour, final String name, final User user, final Location location,
               final String createdAt, final String updatedAt) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.imageUrl = imageUrl;
        this.colour = colour;
        this.name = name;
        this.user = user;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Car(final String name, final String registrationNumber, final String colour,
               final String type, final String imageUrl) {
        this.id = 0;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.colour = colour;
        this.name = name;
        this.imageUrl = imageUrl;
        this.user = null;
        this.location = null;
        this.createdAt = "";
        this.updatedAt = "";
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

    public String getImageUrl() {
        return imageUrl;
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
