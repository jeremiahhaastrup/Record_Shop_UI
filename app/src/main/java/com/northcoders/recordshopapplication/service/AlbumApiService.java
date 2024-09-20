package com.northcoders.recordshopapplication.service;

import com.northcoders.recordshopapplication.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlbumApiService {

    @GET("/api/v1/albums")
    Call<List<Album>> getAllAlbums();

    @POST("/api/v1/albums")
    Call<Album> addAlbum(@Body Album album);
}
