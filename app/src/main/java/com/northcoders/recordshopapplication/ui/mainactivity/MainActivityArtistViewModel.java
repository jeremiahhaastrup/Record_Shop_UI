package com.northcoders.recordshopapplication.ui.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.repository.ArtistRepository;

import java.io.File;
import java.util.List;

public class MainActivityArtistViewModel extends AndroidViewModel {

    private final ArtistRepository artistRepository;

    public MainActivityArtistViewModel(@NonNull Application application) {
        super(application);
        this.artistRepository = new ArtistRepository(application);
    }

    public LiveData<List<Artist>> getAllArtists() {
        return artistRepository.getMutableLiveData();
    }

    public void addArtist(Artist artist, File file) {
        artistRepository.addArtist(artist, file);
    }

    public void updateArtist(long id, Artist artist, File file) {
        artistRepository.updateArtist(id, artist, file);
    }

    public void deleteArtist(long id) {
        artistRepository.deleteArtist(id);
    }
}
