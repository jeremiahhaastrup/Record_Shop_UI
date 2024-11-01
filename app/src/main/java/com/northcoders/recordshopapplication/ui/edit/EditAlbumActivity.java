package com.northcoders.recordshopapplication.ui.edit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityEditAlbumBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class EditAlbumActivity extends AppCompatActivity {

    private static final String ALBUM_KEY = "album";
    private ActivityEditAlbumBinding activityEditAlbumBinding;
    private Album album;
    private EditAlbumClickHandler handler;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_album);

        activityEditAlbumBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_album);
        album = getIntent().getParcelableExtra(ALBUM_KEY, Album.class);
        MainActivityAlbumViewModel viewmodel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        activityEditAlbumBinding.setAlbum(album);

        handler = new EditAlbumClickHandler(album, this, viewmodel);
        activityEditAlbumBinding.setClickHandler(handler);

        Glide.with(this)
                .load(album != null ? album.getImageUrl() : null)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityEditAlbumBinding.addAlbumCoverView);
    }
}