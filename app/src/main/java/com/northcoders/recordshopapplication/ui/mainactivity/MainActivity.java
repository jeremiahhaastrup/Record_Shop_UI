package com.northcoders.recordshopapplication.ui.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Album> albums;

    private ArrayList<Album> albumFilteredList;
    private AlbumAdapter albumAdapter;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler mainActivityClickHandler;
    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;

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
}