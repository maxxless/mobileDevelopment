package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class User {

    @SerializedName("userId")
    @Expose
    private final int id;

    @SerializedName("userFirstName")
    @Expose
    private final String firstName;

    @SerializedName("userLastName")
    @Expose
    private final String lastName;

    @SerializedName("userEmail")
    @Expose
    private final String email;

    @SerializedName("userFares")
    @Expose
    private final Set<Fare> fares;

    @SerializedName("createdAt")
    @Expose
    private final String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private final String updatedAt;

    public User(final int id, final String firstName, final String lastName, final String email,
                final Set<Fare> fares, final String createdAt, final String updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fares = fares;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Fare> getFares() {
        return fares;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
