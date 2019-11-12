package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Location {

    @SerializedName("locationId")
    @Expose
    private int id;

    @SerializedName("locationName")
    @Expose
    private String name;

    @SerializedName("locationDescription")
    @Expose
    private String description;

    @SerializedName("locationCars")
    @Expose
    private Set<Car> cars;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Car> getCars() {
        return cars;
    }
}
