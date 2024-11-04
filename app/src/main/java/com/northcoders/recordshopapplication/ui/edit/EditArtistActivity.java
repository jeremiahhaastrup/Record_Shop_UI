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

        artistDateOfBirthButton = findViewById(R.id.artistDateOfBirthButton);
        artistPlaceOfBirthText = findViewById(R.id.artistPlaceOfBirth);

        artistDateOfBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBirthDatePickerDialog();
            }
        });

        changeArtistImageButton = findViewById(R.id.changeArtistImageButton);

        changeArtistImageButton.setOnClickListener(v ->
                photoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        path = artist.getImageUrl();

        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            activityEditArtistBinding.addArtistImageView.setImageBitmap(bitmap);
                            inputStream.close();

                            String mimeType = getContentResolver().getType(uri);
                            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

                            File tempFile = new File(getCacheDir(), "file." + extension);
                            Log.i("Image Extension", "MIME type: " + mimeType + ", Extension: " + extension);
                            FileOutputStream fos = new FileOutputStream(tempFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();

                            newPath = tempFile.getAbsolutePath();
                        } catch (IOException e) {
                            Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        updateArtistButton = findViewById(R.id.updateArtistButton);

        updateArtistButton.setOnClickListener(v -> {
            File newFileToUpload = newPath != null ? new File(newPath) : null;

            if (artist.getName() == null) {
                Toast.makeText(this, "Artist Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else if (artist.getDateOfBirth() == null) {
                Toast.makeText(this, "Artist Date of Birth Cannot Be Empty. Select or Create a New Artist If Not Available", Toast.LENGTH_SHORT).show();
            } else if (artist.getPlaceOfBirth() == null) {
                Toast.makeText(this, "Artist Place of Birth Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else if (artist.getBiography() == null) {
                Toast.makeText(this, "Artist Biography Cannot Be Empty ", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MainActivity.class);

                Artist newArtist = new Artist(
                        artist.getName(),
                        artist.getImageUrl(),
                        artist.getBiography(),
                        artist.getArtist_id(),
                        artist.getDateOfBirth(),
                        artist.getPlaceOfBirth()
                );

                mainActivityArtistViewModel.updateArtist(newArtist.getArtist_id(), newArtist, newFileToUpload);
                this.startActivity(intent);
            }
        });
    }

    private void openBirthDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                artistPlaceOfBirthText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }
}