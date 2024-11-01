package com.northcoders.recordshopapplication.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.service.AlbumApiService;
import com.northcoders.recordshopapplication.service.RetrofitInstance;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private MutableLiveData<List<Album>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public AlbumRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Album>> getMutableLiveData() {
        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<List<Album>> call = albumApiService.getAllAlbums();

        call.enqueue(new Callback<List<Album>>(){
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                List<Album> albums = response.body();
                mutableLiveData.setValue(albums);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable throwable) {

            }
        });
        return mutableLiveData;
    }

    public void addAlbum(Album album, File file) {

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(file, MediaType.parse("image/*")));
        RequestBody albumBody = RequestBody.create(new Gson().toJson(album), MediaType.parse("application/json"));

        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<Album> call = albumApiService.addAlbum(albumBody, filePart);

        String fileName = file.getName();
        if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            Toast.makeText(application.getApplicationContext(), "Unsupported image format", Toast.LENGTH_SHORT).show();
            return;
        }

        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(application.getApplicationContext(),
                            "Album added!",
                            Toast.LENGTH_SHORT).show();
                    Log.i("SUCCESSFUL RESPONSE", response.body().toString());
                } else {
                    Toast.makeText(application.getApplicationContext(),
                            "Failed to add album. Error: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable throwable) {
                Log.e("API Error", "Failure: " + throwable.getMessage(), throwable);
                Toast.makeText(application.getApplicationContext(),
                        "Album cannot be added!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
