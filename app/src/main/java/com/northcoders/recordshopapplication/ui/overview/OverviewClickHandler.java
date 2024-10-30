package com.northcoders.recordshopapplication.ui.albumOverview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.edit.EditAlbumActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class AlbumOverviewClickHandler {

    private Context context;
    private Album album;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;

    public AlbumOverviewClickHandler(Context context, Album album, MainActivityAlbumViewModel mainActivityAlbumViewModel) {
        this.context = context;
        this.album = album;
        this.mainActivityAlbumViewModel = mainActivityAlbumViewModel;
    }

    public void onBackBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        context.startActivity(intent);
    }

    public void onEditBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), EditAlbumActivity.class);
        context.startActivity(intent);
    }

    // get specific artist
}