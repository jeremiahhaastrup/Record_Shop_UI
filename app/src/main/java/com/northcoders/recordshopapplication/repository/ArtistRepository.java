package com.northcoders.recordshopapplication.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.service.AlbumApiService;
import com.northcoders.recordshopapplication.service.RetrofitInstance;

import java.util.List;

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
}
