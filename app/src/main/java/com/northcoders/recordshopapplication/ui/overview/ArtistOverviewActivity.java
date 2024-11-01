package com.northcoders.recordshopapplication.ui.overview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityArtistOverviewBinding;
import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class ArtistOverviewActivity extends AppCompatActivity {

    private static final String ARTIST_KEY = "artist";
    private ActivityArtistOverviewBinding activityArtistOverviewBinding;
    private Artist artist;
    private ArtistOverviewClickHandler handler;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_overview);

        activityArtistOverviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_artist_overview);
        artist = getIntent().getParcelableExtra(ARTIST_KEY);
        MainActivityAlbumViewModel viewmodel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        activityArtistOverviewBinding.setArtist(artist);

        if (artist == null) {
            Log.e("ArtistOverviewActivity", "Artist data is null:");
        } else {
            Log.d("ArtistOverviewActivity", "Artist Name: " + artist.getName());
        }


        handler = new ArtistOverviewClickHandler(this, artist, viewmodel);
        activityArtistOverviewBinding.setClickHandler(handler);

        Glide.with(this)
                .load(artist != null ? artist.getImageUrl() : null)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityArtistOverviewBinding.addArtistImageView);
    }
}