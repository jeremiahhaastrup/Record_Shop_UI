package com.northcoders.recordshopapplication.ui.create;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

public class CreateClickHandlers {

    private Album album;
    private Context context;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;

    public CreateClickHandlers(Album album, Context context, MainActivityAlbumViewModel mainActivityAlbumViewModel) {
        this.album = album;
        this.context = context;
        this.mainActivityAlbumViewModel = mainActivityAlbumViewModel;
    }

    public void onSubmitAlbumBtnClicker(View view) {

        String stockString = String.valueOf(album.getStock());

        if (album.getTitle() == null) {
            Toast.makeText(context, "Album Title Cannot Be Empty", Toast.LENGTH_SHORT).show();
        } else if (album.getArtist() == null) {
            Toast.makeText(context, "Album Artist Cannot Be Empty. Select or Create a New Artist If Not Available", Toast.LENGTH_SHORT).show();
        } else if (album.getGenre() == null) {
            Toast.makeText(context, "Album Genre Cannot Be Empty", Toast.LENGTH_SHORT).show();
        } else if (album.getReleaseDate() == null) {
            Toast.makeText(context, "Album Release Cannot Be Empty ", Toast.LENGTH_SHORT).show();
        } else if (album.getStock() < 0) {
            Toast.makeText(context, "Album Stock Cannot Be Negative", Toast.LENGTH_SHORT).show();
        } else if (stockString.trim().isEmpty()) {
            Toast.makeText(context, "Album Stock Cannot Be Empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(view.getContext(), MainActivityAlbumViewModel.class);
            Album newAlbum = new Album(
                    album.getAlbum_id(),
                    album.getStock(),
                    album.getTitle(),
                    0,
                    album.getImageUrl(),
                    album.getDescription(),
                    album.getReleaseDate(),
                    album.getArtist(),
                    album.getGenre()
            );

            mainActivityAlbumViewModel.addAlbum(newAlbum);
            context.startActivity(intent);
        }


    }

    public void onCancelBtnClicker(View view) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Back to Home")
                .setMessage("Are you sure you want to leave this page? All progress will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
