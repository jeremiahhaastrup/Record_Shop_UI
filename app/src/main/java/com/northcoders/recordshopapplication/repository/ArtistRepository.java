package com.northcoders.recordshopapplication.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.model.Artist;
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

public class ArtistRepository {

    private MutableLiveData<List<Artist>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public ArtistRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Artist>> getMutableLiveData() {
        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<List<Artist>> call = albumApiService.getAllArtists();

        call.enqueue(new Callback<List<Artist>>(){
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                List<Artist> artists = response.body();
                mutableLiveData.setValue(artists);
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable throwable) {

            }
        });
        return mutableLiveData;
    }

    public void addArtist(Artist artist, File file) {

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(file, MediaType.parse("image/*")));
        RequestBody artistBody = RequestBody.create(new Gson().toJson(artist), MediaType.parse("application/json"));

        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<Artist> call = albumApiService.addArtist(artistBody, filePart);

        String fileName = file.getName();
        if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            Toast.makeText(application.getApplicationContext(), "Unsupported image format", Toast.LENGTH_SHORT).show();
            return;
        }

        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(application.getApplicationContext(),
                            "Artist added!",
                            Toast.LENGTH_SHORT).show();
                    Log.i("SUCCESSFUL RESPONSE", response.body().toString());
                } else {
                    Toast.makeText(application.getApplicationContext(),
                            "Failed to add artist. Error: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable throwable) {
                Log.e("API Error", "Failure: " + throwable.getMessage(), throwable);
                Toast.makeText(application.getApplicationContext(),
                        "Artist cannot be added!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
