package com.northcoders.recordshopapplication.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.northcoders.recordshopapplication.BR;

public class Album extends BaseObservable {
    @SerializedName("album_id")
    private int album_id;

    @SerializedName("title")
    private String title;

    @SerializedName("stock")
    private int stock;

    @SerializedName("sales")
    private int sales;

    @SerializedName("releaseDate")
    private String releaseDate;

    @SerializedName("genre")
    private String genre;

    @SerializedName("artist")
    private Artist artist;

    public Album(int album_id, int stock, String title, int sales, String releaseDate, Artist artist, String genre) {
        this.album_id = album_id;
        this.stock = stock;
        this.title = title;
        this.sales = sales;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.genre = genre;
    }

    public Album() {
    }

    @Bindable
    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
        notifyPropertyChanged(BR.album_id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        notifyPropertyChanged(BR.stock);
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        notifyPropertyChanged(BR.releaseDate);
    }

    @Bindable
    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
        notifyPropertyChanged(BR.sales);
    }

    @Bindable
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        notifyPropertyChanged(BR.genre);
    }

    @Bindable
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        notifyPropertyChanged(BR.artist);
    }
}
