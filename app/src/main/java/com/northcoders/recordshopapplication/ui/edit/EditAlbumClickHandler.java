package com.northcoders.recordshopapplication.ui.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;
import com.northcoders.recordshopapplication.ui.overview.AlbumOverviewActivity;

public class EditAlbumClickHandler {
    private final Album album;
    private final Context context;
    private final MainActivityAlbumViewModel mainActivityAlbumViewModel;
    private long albumId;

    public EditAlbumClickHandler(Album album, Context context, MainActivityAlbumViewModel mainActivityAlbumViewModel) {
        this.album = album;
        this.context = context;
        this.mainActivityAlbumViewModel = mainActivityAlbumViewModel;
    }

    public void onCancelAlbumEditBtn(View view) {
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

    public void onDeleteAlbumBtn(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle(String.format("Delete %s", album.getTitle()))
                .setMessage("This action cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        albumId = album.getAlbum_id();
                        mainActivityAlbumViewModel.deleteAlbum(albumId);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
