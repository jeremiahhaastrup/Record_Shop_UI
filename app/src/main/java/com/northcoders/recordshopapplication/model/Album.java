package com.northcoders.recordshopapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.northcoders.recordshopapplication.BR;

public class Album extends BaseObservable implements Parcelable {
    @SerializedName("album_id")
    private int album_id;

    @SerializedName("title")
    private String title;

    @SerializedName("stock")
    private int stock;

    @SerializedName("sales")
    private int sales;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("releaseDate")
    private String releaseDate;

    @SerializedName("genre")
    private String genre;

    @SerializedName("artist")
    private Artist artist;

    public Album(int album_id, int stock, String title, int sales, String imageUrl, String description, String releaseDate, Artist artist, String genre) {
        this.album_id = album_id;
        this.stock = stock;
        this.title = title;
        this.sales = sales;
        this.imageUrl = imageUrl;
        this.description = description;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.genre = genre;
    }

    public Album() {
    }

    protected Album(Parcel in) {
        album_id = in.readInt();
        title = in.readString();
        stock = in.readInt();
        sales = in.readInt();
        imageUrl = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        genre = in.readString();
        artist = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
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

    @Override
    public int describeContents() {
        return 0;
    }

//    int album_id, int stock, String title, int sales, String imageUrl, String description, String releaseDate, Artist artist, String genr

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(album_id);
        parcel.writeInt(stock);
        parcel.writeString(title);
        parcel.writeInt(sales);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
        parcel.writeString(releaseDate);
        parcel.writeParcelable(artist, flags);
        parcel.writeString(genre);
    }
}
