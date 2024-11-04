package com.northcoders.recordshopapplication.ui.create;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;

public class CreateClickHandlers {

    private final Album album;
    private final Context context;

    public CreateClickHandlers(Album album, Context context) {
        this.album = album;
        this.context = context;
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
