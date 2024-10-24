package com.northcoders.recordshopapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.northcoders.recordshopapplication.BR;

public class Artist extends BaseObservable implements Parcelable {

    @SerializedName("artist_id")
    private int artist_id;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("biography")
    private String biography;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("placeOfBirth")
    private String placeOfBirth;

    public Artist(String name, String imageUrl, String biography, int artist_id, String dateOfBirth, String placeOfBirth) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.biography = biography;
        this.artist_id = artist_id;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        artist_id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        biography = in.readString();
        dateOfBirth = in.readString();
        placeOfBirth = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Bindable
    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
        notifyPropertyChanged(BR.artist_id);
    }

    @Bindable
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        notifyPropertyChanged(BR.dateOfBirth);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
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
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
        notifyPropertyChanged(BR.biography);
    }

    @Bindable
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        notifyPropertyChanged(BR.placeOfBirth);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(artist_id);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(biography);
        parcel.writeString(dateOfBirth);
        parcel.writeString(placeOfBirth);
    }

}
