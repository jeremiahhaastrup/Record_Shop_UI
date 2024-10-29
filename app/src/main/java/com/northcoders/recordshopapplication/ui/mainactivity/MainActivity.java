package com.northcoders.recordshopapplication.ui.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityMainBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.ui.create.CreateActivity;
import com.northcoders.recordshopapplication.ui.library.LibraryActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Album> albums;
    private ArrayList<Album> albumFilteredList;

    private List<Album> albumsByGenre;
    private AlbumAdapter albumAdapter;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler mainActivityClickHandler;
    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;

    private Button buttonAll, buttonAfrobeats, buttonRAndB, buttonHipHop, buttonJazz, buttonSalsa, buttonHouse, buttonDrumAndBass, buttonClassical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityAlbumViewModel = new ViewModelProvider(this).get(MainActivityAlbumViewModel.class);
        mainActivityClickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setAddAlbumHandler(mainActivityClickHandler);

        getAllAlbums();

        bottomNavigationView = activityMainBinding.bottomNavigationHome;
        bottomNavigationView.setSelectedItemId(R.id.homeView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.create) {
                    intent = new Intent(MainActivity.this, CreateActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.library) {
                    intent = new Intent(MainActivity.this, LibraryActivity.class);
                    startActivity(intent);
                    return true;
                } else return id == R.id.homeView;
            }
        });

        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAlbumList(newText);
                recyclerView.scrollToPosition(0);
                albumAdapter.notifyDataSetChanged();
                return true;
            }
        });

        buttonAll = findViewById(R.id.all);
        buttonAfrobeats = findViewById(R.id.afrobeats);
        buttonClassical = findViewById(R.id.classical);
        buttonHouse = findViewById(R.id.house);
        buttonJazz = findViewById(R.id.jazz);
        buttonDrumAndBass = findViewById(R.id.drumAndBass);
        buttonSalsa = findViewById(R.id.salsa);
        buttonHipHop = findViewById(R.id.hiphop);
        buttonRAndB = findViewById(R.id.rAndB);

        buttonAll.setOnClickListener(view -> { setSelectedButton(buttonAll); getAllAlbumsByGenre("All"); });
        buttonAfrobeats.setOnClickListener(view -> { setSelectedButton(buttonAfrobeats); getAllAlbumsByGenre("Afrobeats"); });
        buttonClassical.setOnClickListener(view -> { setSelectedButton(buttonClassical); getAllAlbumsByGenre("Classical"); });
        buttonHouse.setOnClickListener(view -> { setSelectedButton(buttonHouse); getAllAlbumsByGenre("House"); });
        buttonJazz.setOnClickListener(view -> { setSelectedButton(buttonJazz); getAllAlbumsByGenre("Jazz"); });
        buttonDrumAndBass.setOnClickListener(view -> { setSelectedButton(buttonDrumAndBass); getAllAlbumsByGenre("Drum And Bass"); });
        buttonSalsa.setOnClickListener(view -> { setSelectedButton(buttonSalsa); getAllAlbumsByGenre("Salsa"); });
        buttonHipHop.setOnClickListener(view -> { setSelectedButton(buttonHipHop); getAllAlbumsByGenre("Hip-Hop"); });
        buttonRAndB.setOnClickListener(view -> { setSelectedButton(buttonRAndB); getAllAlbumsByGenre("R&B"); });


    }

    private void setSelectedButton(Button selectedButton) {
        buttonAll.setSelected(false);
        buttonAfrobeats.setSelected(false);
        buttonRAndB.setSelected(false);
        buttonHipHop.setSelected(false);
        buttonJazz.setSelected(false);
        buttonSalsa.setSelected(false);
        buttonHouse.setSelected(false);
        buttonDrumAndBass.setSelected(false);
        buttonClassical.setSelected(false);

        selectedButton.setSelected(true);
    }

    private void getAllAlbums() {
        mainActivityAlbumViewModel.getAllAlbums().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albumsFromLiveData) {
                albums = (ArrayList<Album>) albumsFromLiveData;
                displayInRecyclerView();
            }
        });
    }

    private void displayInRecyclerView() {
        recyclerView = activityMainBinding.RecyclerView;
        albumAdapter = new AlbumAdapter(albums, this);
        recyclerView.setAdapter(albumAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        albumAdapter.notifyDataSetChanged();
    }

    private void filterAlbumList(String text) {
        albumFilteredList = new ArrayList<>();
        for (Album album : albums) {
            if (album.getTitle().toLowerCase().contains(text.toLowerCase())
                    || album.getGenre().toLowerCase().contains(text.toLowerCase())
                    || album.getArtist().getName().toLowerCase().contains(text.toLowerCase())) {
                albumFilteredList.add(album);
            }
        }
        if (albumFilteredList.isEmpty()) {
            Toast.makeText(MainActivity.this, "No album found", Toast.LENGTH_SHORT).show();
        }
        albumAdapter.setAlbumFilteredList(albumFilteredList);
    }

    private void getAllAlbumsByGenre(String selectedGenre) {
        if(selectedGenre.equalsIgnoreCase("All")) {
            albumAdapter.setAlbumFilteredList(albums);
        } else {
            albumsByGenre = albums.stream()
                    .filter(album -> album.getGenre().equalsIgnoreCase(selectedGenre))
                    .collect(Collectors.toList());

            albumAdapter.setAlbumFilteredList(albumsByGenre);
        }
        recyclerView.scrollToPosition(0);
        albumAdapter.notifyDataSetChanged();
    }
}