package com.northcoders.recordshopapplication.ui.edit;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityEditArtistBinding;
import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityArtistViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class EditArtistActivity extends AppCompatActivity {

    private static final String ARTIST_KEY = "artist";
    ActivityResultLauncher<PickVisualMediaRequest> photoPickerLauncher;
    Calendar calendar = Calendar.getInstance();
    int mYear = calendar.get(Calendar.YEAR);
    int mMonth = calendar.get(Calendar.MONTH);
    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
    private ActivityEditArtistBinding activityEditArtistBinding;
    private Artist artist;
    private EditArtistClickHandler handler;
    private TextView artistPlaceOfBirthText;
    private String path;
    private String newPath;
    private Button artistDateOfBirthButton, changeArtistImageButton, updateArtistButton;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_artist);

        activityEditArtistBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_artist);
        artist = getIntent().getParcelableExtra(ARTIST_KEY);
        if (artist == null) {
            Log.e("EditArtistActivity", "Failed to receive Artist data.");
        } else {
            Log.d("EditArtistActivity", "Received Artist: " + artist.getName());
        }
        MainActivityArtistViewModel mainActivityArtistViewModel = new ViewModelProvider(this).get(MainActivityArtistViewModel.class);
        activityEditArtistBinding.setArtist(artist);

        handler = new EditArtistClickHandler(this, artist, mainActivityArtistViewModel);
        activityEditArtistBinding.setClickHandler(handler);

        Glide.with(this)
                .load(artist != null ? artist.getImageUrl() : null)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityEditArtistBinding.addArtistImageView);
    }
}