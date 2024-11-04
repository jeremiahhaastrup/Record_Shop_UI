package com.northcoders.recordshopapplication.ui.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityArtistViewModel;
import com.northcoders.recordshopapplication.ui.overview.ArtistOverviewActivity;

public class EditArtistClickHandler {

    private final Context context;
    private final Artist artist;
    private final MainActivityArtistViewModel mainActivityArtistViewModel;
    private long artistId;

    public EditArtistClickHandler(Context context, Artist artist, MainActivityArtistViewModel mainActivityAlbumViewModel) {
        this.context = context;
        this.artist = artist;
        this.mainActivityArtistViewModel = mainActivityAlbumViewModel;
    }

    public void onCancelArtistEditBtn(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Back to Artist")
                .setMessage("Are you sure you want to leave this page? All progress will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), ArtistOverviewActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void onDeleteArtistBtn(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(String.format("Delete %s", artist.getName()))
                .setMessage("This action cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        artistId = artist.getArtist_id();
                        mainActivityArtistViewModel.deleteArtist(artistId);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
