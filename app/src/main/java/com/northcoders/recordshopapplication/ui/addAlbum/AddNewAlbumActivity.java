package com.northcoders.recordshopapplication.ui.addAlbum;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityAddNewAlbumBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivityViewModel;

import java.util.ArrayList;

public class AddNewAlbumActivity extends AppCompatActivity {

    private ActivityAddNewAlbumBinding activityAddNewAlbumBinding;
    private AddAlbumClickHandlers addAlbumClickHandlers;
    private Album album;

    private TextView releaseDateText;

    private TextView genreText;
    private Dialog genreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_album);

        album = new Album();

        activityAddNewAlbumBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_album);

        MainActivityViewModel model = new ViewModelProvider(this).get(MainActivityViewModel.class);

        addAlbumClickHandlers = new AddAlbumClickHandlers(album, this, model);
        activityAddNewAlbumBinding.setAlbum(album);
        activityAddNewAlbumBinding.setClickHandler(addAlbumClickHandlers);

        releaseDateText = findViewById(R.id.albumReleaseDate);
        Button releaseDateButton = findViewById(R.id.addReleaseDateButton);

        releaseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        genreText = findViewById(R.id.albumGenre);

        genreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genreDialog = new Dialog(AddNewAlbumActivity.this);
                genreDialog.setContentView(R.layout.genre_searchable_spinner);
                genreDialog.show();

                EditText genreEditText = genreDialog.findViewById(R.id.genreEditText);
                ListView genreListView = genreDialog.findViewById(R.id.genreListView);

                Resources res = getResources();
                String[] genres = res.getStringArray(R.array.genres);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        AddNewAlbumActivity.this,
                        R.layout.genre_list_item,
                        R.id.genreTextView,
                        genres
                );

                genreListView.setAdapter(adapter);

                genreEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                genreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        genreText.setText(adapter.getItem(position));
                        genreDialog.dismiss();
                    }
                });
            }
        });
 }

    private void openDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                releaseDateText.setText(String.format("%s/%s/%s", dayOfMonth, month + 1, year));
            }
        }, 2024, 0, 1);
        dialog.show();
    }
}