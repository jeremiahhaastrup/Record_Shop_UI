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
import com.bumptech.glide.request.RequestOptions;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityAlbumOverviewBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.edit.EditAlbumActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class AlbumOverviewActivity extends AppCompatActivity {

    private static final String ALBUM_KEY = "album";
    private ActivityAlbumOverviewBinding activityAlbumOverviewBinding;
    private Album album;
    private AlbumOverviewClickHandler handler;
    private ImageButton editAlbumButton;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_overview);

        activityAlbumOverviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_overview);
        album = getIntent().getParcelableExtra(ALBUM_KEY, Album.class);
        MainActivityAlbumViewModel viewmodel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        activityAlbumOverviewBinding.setAlbum(album);

        handler = new AlbumOverviewClickHandler(this, album, viewmodel);
        activityAlbumOverviewBinding.setClickHandler(handler);

        if (album.getArtist() == null) {
            Log.e("AlbumOverviewActivity", "Artist is null in AlbumOverviewActivity");
        } else {
            Log.d("AlbumOverviewActivity", "Artist Name: " + album.getArtist().toString());
        }

        Glide.with(this)
                .load(album != null ? album.getImageUrl() : null)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityAlbumOverviewBinding.addAlbumCoverView);

        Glide.with(this)
                .load(album != null ? album.getArtist().getImageUrl() : null)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityAlbumOverviewBinding.artistImageView);

        editAlbumButton = findViewById(R.id.editAlbumButton);
        editAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumOverviewActivity.this, EditAlbumActivity.class);
                intent.putExtra(ALBUM_KEY, album);
                startActivity(intent);
            }
        });

    }
}