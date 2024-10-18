package com.northcoders.recordshopapplication.ui.mainactivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityMainBinding;
import com.northcoders.recordshopapplication.model.Album;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAlbums extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Album> albums;
    private AlbumAdapter albumAdapter;
    private MainActivityAlbumViewModel mainActivityAlbumViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler mainActivityClickHandler;

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
}