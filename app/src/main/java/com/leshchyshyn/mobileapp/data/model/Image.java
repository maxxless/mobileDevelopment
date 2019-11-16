package com.leshchyshyn.mobileapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("albumId")
    @Expose
    private final int albumId;

    @SerializedName("id")
    @Expose
    private final int id;

    @SerializedName("title")
    @Expose
    private final String title;

    @SerializedName("url")
    @Expose
    private final String url;

    @SerializedName("thumbnailUrl")
    @Expose
    private final String thumbnailUrl;

    public Image(final int albumId, final int id, final String title, final String url,
                 final String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
