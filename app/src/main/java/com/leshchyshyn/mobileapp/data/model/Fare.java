package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fare {

    @SerializedName("fareId")
    @Expose
    private final int id;

    @SerializedName("fareUuid")
    @Expose
    private final String uuid;

    @SerializedName("fareStatus")
    @Expose
    private final String status;

    @SerializedName("fareUser")
    @Expose
    private final User user;

    @SerializedName("createdAt")
    @Expose
    private final String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private final String updatedAt;

    public Fare(final int id, final String uuid, final String status, final User user,
                final String createdAt, final String updatedAt) {
        this.id = id;
        this.uuid = uuid;
        this.status = status;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
