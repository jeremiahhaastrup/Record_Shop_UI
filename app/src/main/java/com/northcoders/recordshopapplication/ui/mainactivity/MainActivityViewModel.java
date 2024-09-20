package com.northcoders.recordshopapplication.ui.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.repository.AlbumRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private AlbumRepository albumRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.albumRepository = new AlbumRepository(application);
    }

    public LiveData<List<Album>> getAllAlbums() {
        return albumRepository.getMutableLiveData();
    }

    public void addAlbum(Album album) {
        albumRepository.addAlbum(album);
    }
}
