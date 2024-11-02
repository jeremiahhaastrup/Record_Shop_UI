package com.northcoders.recordshopapplication.ui.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.repository.AlbumRepository;

import java.io.File;
import java.util.List;

public class MainActivityAlbumViewModel extends AndroidViewModel {

    private AlbumRepository albumRepository;

    public MainActivityAlbumViewModel(@NonNull Application application) {
        super(application);
        this.albumRepository = new AlbumRepository(application);
    }

    public LiveData<List<Album>> getAllAlbums() {
        return albumRepository.getMutableLiveData();
    }

    public void addAlbum(Album album, File file) {
        albumRepository.addAlbum(album, file);
    }

    public void updateAlbum(long id, Album album, File file) {
        if (file != null){
            albumRepository.updateAlbum(id, album, file);
        } else {
            albumRepository.updateAlbum(id, album, null);
        }
    }

    public void deleteAlbum(long id) {
        albumRepository.deleteAlbum(id);
    }
}
