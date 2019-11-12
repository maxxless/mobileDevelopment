package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fare {

    @SerializedName("fareId")
    @Expose
    private int id;

    @SerializedName("fareUuid")
    @Expose
    private String uuid;

    @SerializedName("fareStatus")
    @Expose
    private String status;

    @SerializedName("fareUser")
    @Expose
    private User user;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
