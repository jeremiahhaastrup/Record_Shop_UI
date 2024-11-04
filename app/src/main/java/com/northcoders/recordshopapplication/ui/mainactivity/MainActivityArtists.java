package com.northcoders.recordshopapplication.ui.mainactivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityMainArtistsBinding;
import com.northcoders.recordshopapplication.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class MainActivityArtists extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Artist> artists;
    private ArtistAdapter artistAdapter;
    private MainActivityArtistViewModel mainActivityArtistViewModel;
    private ActivityMainArtistsBinding activityMainArtistsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_artists);

        activityMainArtistsBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_artists);
        mainActivityArtistViewModel = new ViewModelProvider(this).get(MainActivityArtistViewModel.class);


        getAllArtists();
    }

    private void getAllArtists() {
        mainActivityArtistViewModel.getAllArtists().observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artistList) {
                artists = (ArrayList<Artist>) artistList;
                displayInRecyclerView();
            }
        });
    }

    private void displayInRecyclerView() {
        recyclerView = activityMainArtistsBinding.RecyclerView;
        artistAdapter = new ArtistAdapter(artists, this);
        recyclerView.setAdapter(artistAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        artistAdapter.notifyDataSetChanged();
    }
}