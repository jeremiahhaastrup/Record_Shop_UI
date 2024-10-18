package com.northcoders.recordshopapplication.ui.addAlbum;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityAddBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.search.SearchActivity;
import com.northcoders.recordshopapplication.ui.library.LibraryActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityAlbumViewModel;

import java.util.Calendar;


public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding activityAddBinding;
    private AddClickHandlers addClickHandlers;
    private Album album;

    private TextView releaseDateText;
    private Button releaseDateButton;
    private AutoCompleteTextView genreDropdownMenu, artistDropdownMenu;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        EdgeToEdge.enable(this);

        album = new Album();

        activityAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);

        MainActivityAlbumViewModel model = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);

        addClickHandlers = new AddClickHandlers(album, this, model);
        activityAddBinding.setAlbum(album);
        activityAddBinding.setClickHandler(addClickHandlers);

        releaseDateText = findViewById(R.id.albumReleaseDate);
        releaseDateButton = findViewById(R.id.albumReleaseDateButton);

        releaseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        initialiseGenreDropdownMenu();

        bottomNavigationView = activityAddBinding.bottomNavigationCreate;
        bottomNavigationView.setSelectedItemId(R.id.create);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.homeView) {
                    intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.search) {
                    intent = new Intent(AddActivity.this, SearchActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.library) {
                    intent = new Intent(AddActivity.this, LibraryActivity.class);
                    startActivity(intent);
                    return true;
                } else return id == R.id.create;
            }
        });
 }

    private void initialiseGenreDropdownMenu() {
        genreDropdownMenu = activityAddBinding.genreDropdown;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.genres,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreDropdownMenu.setAdapter(adapter);
        genreDropdownMenu.setThreshold(1);
    }

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