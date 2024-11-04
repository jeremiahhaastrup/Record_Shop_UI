package com.northcoders.recordshopapplication.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityArtistViewModel;

public class ArtistOverviewClickHandler {

    private final Context context;
    private final Artist artist;
    private final MainActivityArtistViewModel mainActivityAlbumViewModel;

    public ArtistOverviewClickHandler(Context context, Artist artist, MainActivityArtistViewModel mainActivityAlbumViewModel) {
        this.context = context;
        this.artist = artist;
        this.mainActivityAlbumViewModel = mainActivityAlbumViewModel;
    }

    public void onBackToAlbumFromArtistBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), AlbumOverviewActivity.class);
        context.startActivity(intent);
    }

}
