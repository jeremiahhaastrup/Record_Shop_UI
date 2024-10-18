package com.northcoders.recordshopapplication.ui.addAlbum;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityAddNewAlbumBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

import java.util.Calendar;


public class AddActivity extends AppCompatActivity {

    private ActivityAddNewAlbumBinding activityAddNewAlbumBinding;
    private AddAlbumClickHandlers addAlbumClickHandlers;
    private Album album;

    private TextView releaseDateText;
    private Button releaseDateButton;
    private AutoCompleteTextView genreDropdownMenu, artistDropdownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_album);

        album = new Album();

        activityAddNewAlbumBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_album);

        MainActivityAlbumViewModel model = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);

        addAlbumClickHandlers = new AddAlbumClickHandlers(album, this, model);
        activityAddNewAlbumBinding.setAlbum(album);
        activityAddNewAlbumBinding.setClickHandler(addAlbumClickHandlers);

        releaseDateText = findViewById(R.id.albumReleaseDate);
        releaseDateButton = findViewById(R.id.albumReleaseDateButton);

        releaseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        initialiseGenreDropdownMenu();
//        initialiseArtistDropdownMenu();
 }

    private void initialiseGenreDropdownMenu() {
        genreDropdownMenu = activityAddNewAlbumBinding.genreDropdown;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.genres,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreDropdownMenu.setAdapter(adapter);
        genreDropdownMenu.setThreshold(1);
    }

//    private void initialiseArtistDropdownMenu() {
//        artistDropdownMenu = activityAddNewAlbumBinding.artistDropdown;
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this,
//                R.array.genres,
//                android.R.layout.simple_spinner_item
//        );
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        artistDropdownMenu.setAdapter(adapter);
//        artistDropdownMenu.setThreshold(1);
//    }

    Calendar calendar = Calendar.getInstance();
    int mYear = calendar.get(Calendar.YEAR);
    int mMonth = calendar.get(Calendar.MONTH);
    int mDay = calendar.get(Calendar.DAY_OF_MONTH);

    private void openDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                releaseDateText.setText(String.format("%s/%s/%s", dayOfMonth, month + 1, year));
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }
}