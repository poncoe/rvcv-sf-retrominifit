package com.poncoe.retrofit.recyclerviewsearchfilterexample;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("title")
    private String title;

    @SerializedName("images")
    private String imageUrl;

    @SerializedName("isi")
    private String isi;

    public Movie(String title, String imageUrl, String isi) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.isi = isi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
