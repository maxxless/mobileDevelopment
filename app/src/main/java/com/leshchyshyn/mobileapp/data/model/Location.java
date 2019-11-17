package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Location {

    @SerializedName("locationId")
    @Expose
    private final int id;

    @SerializedName("locationName")
    @Expose
    private final String name;

    @SerializedName("locationDescription")
    @Expose
    private final String description;

    @SerializedName("locationCars")
    @Expose
    private final Set<Car> cars;

    public Location(final int id, final String name, final String description,
                    final Set<Car> cars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cars = cars;
    }

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
