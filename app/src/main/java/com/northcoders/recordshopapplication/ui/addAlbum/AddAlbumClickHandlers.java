package com.northcoders.recordshopapplication.ui.addAlbum;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityViewModel;

public class AddAlbumClickHandlers {

    private Album album;
    private Context context;
    private MainActivityViewModel mainActivityViewModel;

    public AddAlbumClickHandlers(Album album, Context context, MainActivityViewModel mainActivityViewModel) {
        this.album = album;
        this.context = context;
        this.mainActivityViewModel = mainActivityViewModel;
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
            Intent intent = new Intent(context, MainActivityViewModel.class);
            Album newAlbum = new Album(
                    album.getAlbum_id(),
                    album.getStock(),
                    album.getTitle(),
                    0,
                    album.getReleaseDate(),
                    album.getArtist(),
                    album.getGenre()
            );

            mainActivityViewModel.addAlbum(newAlbum);
            context.startActivity(intent);
        }


    }

    public void onBackBtnClicker(View view) {
        Intent intent = new Intent(context, MainActivityViewModel.class);
        context.startActivity(intent);

    }
}
