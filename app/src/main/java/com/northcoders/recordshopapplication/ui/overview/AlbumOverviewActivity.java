package com.northcoders.recordshopapplication.ui.overview;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityAlbumOverviewBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class AlbumOverviewActivity extends AppCompatActivity {

    private static final String ALBUM_KEY = "album";
    private ActivityAlbumOverviewBinding activityAlbumOverviewBinding;
    private Album album;
    private OverviewClickHandler handler;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_album_overview);

        activityAlbumOverviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_overview);
        album = getIntent().getParcelableExtra(ALBUM_KEY, Album.class);
        MainActivityAlbumViewModel viewmodel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        activityAlbumOverviewBinding.setAlbum(album);


        handler = new OverviewClickHandler(this, album, viewmodel);
        activityAlbumOverviewBinding.setClickHandler(handler);

    }
}