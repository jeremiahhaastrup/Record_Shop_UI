package com.northcoders.recordshopapplication.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class AlbumOverviewClickHandler {

    private final Context context;
    private final Album album;
    private final MainActivityAlbumViewModel mainActivityAlbumViewModel;

    public AlbumOverviewClickHandler(Context context, Album album, MainActivityAlbumViewModel mainActivityAlbumViewModel) {
        this.context = context;
        this.album = album;
        this.mainActivityAlbumViewModel = mainActivityAlbumViewModel;
    }

    public void onBackHomeBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        context.startActivity(intent);
    }
}
