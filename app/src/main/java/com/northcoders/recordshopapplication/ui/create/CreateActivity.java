package com.northcoders.recordshopapplication.ui.create;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityCreateBinding;
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


public class CreateActivity extends AppCompatActivity {

    ActivityResultLauncher<PickVisualMediaRequest> photoPickerLauncher;
    Calendar calendar = Calendar.getInstance();
    int mYear = calendar.get(Calendar.YEAR);
    int mMonth = calendar.get(Calendar.MONTH);
    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
    private ActivityCreateBinding activityCreateBinding;
    private CreateClickHandlers createClickHandlers;
    private Album album;
    private Artist artist;
    private ArrayList<Artist> artists;
    private ArrayList<Artist> artistNames;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;
    private MainActivityArtistViewModel mainActivityArtistViewModel;
    private TextView releaseDateText, artistDateOfBirthText;
    private Button releaseDateButton, changeAlbumCoverButton, submitAlbumButton, artistDateOfBirthButton, changeArtistButton, submitArtistButton;
    private AutoCompleteTextView genreDropdownMenu, artistDropdownMenu;
    private LinearLayout albumLayout, artistLayout;
    private SwitchCompat switchCompat;
    private String albumPath;
    private String artistPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        album = new Album();
        artist = new Artist();
        artistNames = new ArrayList<>();

        activityCreateBinding = DataBindingUtil.setContentView(this, R.layout.activity_create);
        mainActivityAlbumViewModel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        mainActivityArtistViewModel = new ViewModelProvider(this).get(MainActivityArtistViewModel.class);


        albumLayout = activityCreateBinding.albumLayout;
        artistLayout = activityCreateBinding.artistInfoLayout;
        switchCompat = activityCreateBinding.switchCompat;

        createClickHandlers = new CreateClickHandlers(album, this);
        activityCreateBinding.setAlbum(album);
        activityCreateBinding.setArtist(artist);
        activityCreateBinding.setClickHandler(createClickHandlers);

        releaseDateText = findViewById(R.id.albumReleaseDate);
        releaseDateButton = findViewById(R.id.albumReleaseDateButton);

        releaseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        changeAlbumCoverButton = findViewById(R.id.changeAlbumCoverView);

        changeAlbumCoverButton.setOnClickListener(v ->
                photoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            activityCreateBinding.addAlbumCoverView.setImageBitmap(bitmap);
                            inputStream.close();

                            String mimeType = getContentResolver().getType(uri);
                            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

                            File tempFile = new File(getCacheDir(), "file." + extension);
                            Log.i("Image Extension", "MIME type: " + mimeType + ", Extension: " + extension);
                            FileOutputStream fos = new FileOutputStream(tempFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();

                            albumPath = tempFile.getAbsolutePath();
                        } catch (IOException e) {
                            Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        submitAlbumButton = findViewById(R.id.submitAlbumButton);

        submitAlbumButton.setOnClickListener(v -> {
            String stockString = String.valueOf(album.getStock());

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
                        album.getImageUrl(),
                        album.getDescription(),
                        album.getReleaseDate(),
                        album.getArtist(),
                        album.getGenre()
                );

                mainActivityAlbumViewModel.addAlbum(newAlbum, new File(albumPath));
                this.startActivity(intent);
            }
        });

        artistDateOfBirthButton = findViewById(R.id.artistDateOfBirthButton);
        artistDateOfBirthText = findViewById(R.id.artistDateOfBirth);

        artistDateOfBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBirthDatePickerDialog();
            }
        });

        changeArtistButton = findViewById(R.id.changeArtistButton);

        changeArtistButton.setOnClickListener(v ->
                photoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            activityCreateBinding.addArtistImageView.setImageBitmap(bitmap);
                            inputStream.close();

                            String mimeType = getContentResolver().getType(uri);
                            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

                            File tempFile = new File(getCacheDir(), "file." + extension);
                            Log.i("Image Extension", "MIME type: " + mimeType + ", Extension: " + extension);
                            FileOutputStream fos = new FileOutputStream(tempFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();

                            artistPath = tempFile.getAbsolutePath();
                        } catch (IOException e) {
                            Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        submitArtistButton = findViewById(R.id.submitArtistButton);

        submitArtistButton.setOnClickListener(v -> {

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

                mainActivityArtistViewModel.addArtist(newArtist, new File(artistPath));
                this.startActivity(intent);
            }
        });

        handleToggleBetweenLayouts();
        initialiseGenreDropdownMenu();
        initialiseArtistDropdownMenu();
    }

    private void initialiseGenreDropdownMenu() {
        genreDropdownMenu = activityCreateBinding.genreDropdown;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.genres,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreDropdownMenu.setAdapter(adapter);
        genreDropdownMenu.setThreshold(1);
    }

    private void initialiseArtistDropdownMenu() {
        artistDropdownMenu = activityCreateBinding.artistDropdown;

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

    private void openDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                releaseDateText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void openBirthDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                artistDateOfBirthText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void handleToggleBetweenLayouts() {
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    artistLayout.setVisibility(View.VISIBLE);
                    albumLayout.setVisibility(View.GONE);
                    switchCompat.setText("Create An Artist   ");
                } else {
                    artistLayout.setVisibility(View.GONE);
                    albumLayout.setVisibility(View.VISIBLE);
                    switchCompat.setText("Create An Album   ");
                }
            }
        });
    }
}