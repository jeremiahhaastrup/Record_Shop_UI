package com.northcoders.recordshopapplication.service;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.model.Artist;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AlbumApiService {

    @GET("/api/v1/albums")
    Call<List<Album>> getAllAlbums();

    @POST("/api/v1/albums")
    @Multipart
    Call<Album> addAlbum(@Part("album") RequestBody album, @Part MultipartBody.Part file);

    @PUT("/api/v1/albums/{id}")
    @Multipart
    Call<Album> updateAlbum(@Path("id") long id, @Part("album") RequestBody album, @Part MultipartBody.Part file);

    @DELETE("/api/v1/albums/{id}")
    Call<String> deleteAlbum(@Path("id") long id);

    @GET("/api/v1/artists")
    Call<List<Artist>> getAllArtists();

    @POST("/api/v1/artists")
    @Multipart
    Call<Artist> addArtist(@Part("artist") RequestBody artist, @Part MultipartBody.Part file);

    @PUT("/api/v1/artists/{id}")
    @Multipart
    Call<Artist> updateArtist(@Path("id") long id, @Part("artist") RequestBody artist, @Part MultipartBody.Part file);

    @DELETE("/api/v1/artists/{id}")
    Call<String> deleteArtist(@Path("id") long id);

}
