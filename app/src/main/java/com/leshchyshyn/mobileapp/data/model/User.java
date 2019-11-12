package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class User {

    @SerializedName("userId")
    @Expose
    private int id;

    @SerializedName("userFirstName")
    @Expose
    private String firstName;

    @SerializedName("userLastName")
    @Expose
    private String lastName;

    @SerializedName("userEmail")
    @Expose
    private String email;

    @SerializedName("userFares")
    @Expose
    private Set<Fare> fares;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

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
