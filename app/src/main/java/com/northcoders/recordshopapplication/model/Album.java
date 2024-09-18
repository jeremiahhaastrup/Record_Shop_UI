package com.northcoders.recordshopapplication.model;

public class Album {

    private int album_id;
    private String title;
    private int stock;
    private int sales;
    private String releaseDate;
    private String genre;
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

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
