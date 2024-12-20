package com.northcoders.recordshopapplication.ui.edit;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityEditAlbumBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.model.Artist;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityArtistViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditAlbumActivity extends AppCompatActivity {

    private static final String ALBUM_KEY = "album";
    Calendar calendar = Calendar.getInstance();
    int mYear = calendar.get(Calendar.YEAR);
    int mMonth = calendar.get(Calendar.MONTH);
    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
    private ActivityEditAlbumBinding activityEditAlbumBinding;
    private Album album;
    private EditAlbumClickHandler handler;
    private TextView releaseDateText;
    private Button releaseDateButton, changeAlbumCoverButton, updateAlbumButton;
    private String path;
    private String newPath;
    private AutoCompleteTextView genreDropdownMenu, artistDropdownMenu;
    private MainActivityArtistViewModel mainActivityArtistViewModel;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;
    private ArrayList<Artist> artists;
    private ArrayList<Artist> artistNames;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_album);

        artistNames = new ArrayList<>();

        activityEditAlbumBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_album);
        album = getIntent().getParcelableExtra(ALBUM_KEY, Album.class);
        mainActivityAlbumViewModel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        mainActivityArtistViewModel = new ViewModelProvider(this).get(MainActivityArtistViewModel.class);
        activityEditAlbumBinding.setAlbum(album);

        handler = new EditAlbumClickHandler(album, this, mainActivityAlbumViewModel);
        activityEditAlbumBinding.setClickHandler(handler);

        Glide.with(this)
                .load(album != null ? album.getImageUrl() : null)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(activityEditAlbumBinding.addAlbumCoverView);

        releaseDateText = findViewById(R.id.albumReleaseDate);
        releaseDateButton = findViewById(R.id.albumReleaseDateButton);

        releaseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        changeAlbumCoverButton = findViewById(R.id.changeAlbumCoverView);

        changeAlbumCoverButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            photoPickerLauncher.launch(intent);
        });

        path = album.getImageUrl();

        updateAlbumButton = findViewById(R.id.updateAlbumButton);

        updateAlbumButton.setOnClickListener(v -> {
            String stockString = String.valueOf(album.getStock());
            File newFileToUpload = newPath != null ? new File(newPath) : null;

            if (album.getTitle() == null) {
                Toast.makeText(this, "Album Title Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else if (album.getArtist() == null) {
                Toast.makeText(this, "Album Artist Cannot Be Empty. Select or Create a New Artist If Not Available", Toast.LENGTH_SHORT).show();
            } else if (album.getGenre() == null) {
                Toast.makeText(this, "Album Genre Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else if (album.getReleaseDate() == null) {
                Toast.makeText(this, "Album Release Cannot Be Empty ", Toast.LENGTH_SHORT).show();
            } else if (album.getStock() < 0) {
                Toast.makeText(this, "Album Stock Cannot Be Negative", Toast.LENGTH_SHORT).show();
            } else if (stockString.trim().isEmpty()) {
                Toast.makeText(this, "Album Stock Cannot Be Empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MainActivity.class);

                Album newAlbum = new Album(
                        album.getAlbum_id(),
                        album.getStock(),
                        album.getTitle(),
                        0,
                        newPath != null ? newPath : album.getImageUrl(),
                        album.getDescription(),
                        album.getReleaseDate(),
                        album.getArtist(),
                        album.getGenre()
                );

                mainActivityAlbumViewModel.updateAlbum(newAlbum.getAlbum_id(), newAlbum, newFileToUpload);
                this.startActivity(intent);
            }
        });

        initialiseGenreDropdownMenu();
        initialiseArtistDropdownMenu();
    }

    ActivityResultLauncher<Intent> photoPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == EditAlbumActivity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        if (uri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                activityEditAlbumBinding.addAlbumCoverView.setImageBitmap(bitmap);
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
                                Toast.makeText(EditAlbumActivity.this, "Unable to load image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
    );

    private void openDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                releaseDateText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void initialiseGenreDropdownMenu() {
        genreDropdownMenu = activityEditAlbumBinding.genreDropdown;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.genres,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreDropdownMenu.setAdapter(adapter);
        genreDropdownMenu.setThreshold(1);

        genreDropdownMenu.setOnClickListener(v -> {
            genreDropdownMenu.setText("");
            genreDropdownMenu.showDropDown();
        });

        genreDropdownMenu.setOnItemClickListener((parent, view, position, id) -> {
            genreDropdownMenu.clearFocus();
        });
    }

    private void initialiseArtistDropdownMenu() {
        artistDropdownMenu = activityEditAlbumBinding.artistDropdown;

        ArrayAdapter<Artist> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getAllArtists()
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        artistDropdownMenu.setAdapter(adapter);
        artistDropdownMenu.setThreshold(1);

        artistDropdownMenu.setOnItemClickListener((parent, view, position, id) -> {
            Artist selectedArtist = adapter.getItem(position);
            album.setArtist(selectedArtist);
        });
    }

    private ArrayList<Artist> getAllArtists() {
        mainActivityArtistViewModel.getAllArtists().observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artistList) {
                artists = (ArrayList<Artist>) artistList;
                artistNames.clear();
                artistNames.addAll(artists);
            }
        });
        return artistNames;
    }
}