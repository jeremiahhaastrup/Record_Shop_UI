package com.northcoders.recordshopapplication.ui.overview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityArtistOverviewBinding;
import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.ui.edit.EditArtistActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityArtistViewModel;

public class ArtistOverviewActivity extends AppCompatActivity {

    private static final String ARTIST_KEY = "artist";
    private ActivityArtistOverviewBinding activityArtistOverviewBinding;
    private Artist artist;
    private ArtistOverviewClickHandler handler;

    private ImageButton editArtistButton;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_overview);

        activityArtistOverviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_artist_overview);
        artist = getIntent().getParcelableExtra(ARTIST_KEY);

        if (artist == null) {
            Log.e("ArtistOverviewActivity", "Artist data is null:");
        } else {
            Log.d("ArtistOverviewActivity", "Artist Name: " + artist.getName());
        }

        MainActivityArtistViewModel viewmodel = new ViewModelProvider(this).get(MainActivityArtistViewModel.class);
        activityArtistOverviewBinding.setArtist(artist);

        handler = new ArtistOverviewClickHandler(this, artist, viewmodel);
        activityArtistOverviewBinding.setClickHandler(handler);

        Glide.with(this)
                .load(artist != null ? artist.getImageUrl() : null)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityArtistOverviewBinding.addArtistImageView);

        editArtistButton = findViewById(R.id.editArtistButton);
        editArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (artist != null) {
                    Log.d("ArtistOverviewActivity", "Passing Artist: " + artist.getName());
                    Intent intent = new Intent(ArtistOverviewActivity.this, EditArtistActivity.class);
                    intent.putExtra(ARTIST_KEY, artist);
                    startActivity(intent);
                } else {
                    Log.e("ArtistOverviewActivity", "Artist is null before navigating to EditArtistActivity.");
                }

            }
        });
    }
}