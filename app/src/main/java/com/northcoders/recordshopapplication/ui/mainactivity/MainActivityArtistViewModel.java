package com.northcoders.recordshopapplication.ui.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.repository.ArtistRepository;

import java.util.List;

public class MainActivityArtistViewModel extends AndroidViewModel {

    private ArtistRepository artistRepository;

    public MainActivityArtistViewModel(@NonNull Application application) {
        super(application);
        this.artistRepository = new ArtistRepository(application);
    }

    public LiveData<List<Artist>> getAllArtists() {
        return artistRepository.getMutableLiveData();
    }
}
