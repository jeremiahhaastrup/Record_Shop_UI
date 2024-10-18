package com.northcoders.recordshopapplication.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ActivityLibraryBinding;
import com.northcoders.recordshopapplication.ui.addAlbum.AddActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;
import com.northcoders.recordshopapplication.ui.search.SearchActivity;

public class LibraryActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActivityLibraryBinding activityLibraryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library);

        activityLibraryBinding = DataBindingUtil.setContentView(this, R.layout.activity_library);

        bottomNavigationView = activityLibraryBinding.bottomNavigationLibrary;
        bottomNavigationView.setSelectedItemId(R.id.library);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.homeView) {
                    intent = new Intent(LibraryActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.search) {
                    intent = new Intent(LibraryActivity.this, SearchActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.create) {
                    intent = new Intent(LibraryActivity.this, AddActivity.class);
                    startActivity(intent);
                    return true;
                } else return id == R.id.library;
            }
        });
    }


}