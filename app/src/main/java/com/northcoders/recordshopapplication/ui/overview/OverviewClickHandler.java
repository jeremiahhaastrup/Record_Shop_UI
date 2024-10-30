package com.northcoders.recordshopapplication.ui.overview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.edit.EditAlbumActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class OverviewClickHandler {

    private Context context;
    private Album album;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;

    public OverviewClickHandler(Context context, Album album, MainActivityAlbumViewModel mainActivityAlbumViewModel) {
        this.context = context;
        this.album = album;
        this.mainActivityAlbumViewModel = mainActivityAlbumViewModel;
    }

    public void onBackHomeBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        context.startActivity(intent);
    }

    public void onBackToAlbumFromArtistBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), AlbumOverviewActivity.class);
        context.startActivity(intent);
    }

    public void onCancelAlbumEditBrn(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Back to Album")
                .setMessage("Are you sure you want to leave this page? All progress will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), AlbumOverviewActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    public void onEditBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), EditAlbumActivity.class);
        context.startActivity(intent);
    }

    private void onArtistBtnClicked(View view) {
        Intent intent = new Intent(view.getContext(), ArtistOverviewActivity.class);
        context.startActivity(intent);
    }
}
