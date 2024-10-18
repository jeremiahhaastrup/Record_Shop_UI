package com.northcoders.recordshopapplication.ui.search;

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
import com.northcoders.recordshopapplication.databinding.ActivitySearchBinding;
import com.northcoders.recordshopapplication.ui.addAlbum.AddActivity;
import com.northcoders.recordshopapplication.ui.library.LibraryActivity;
import com.northcoders.recordshopapplication.ui.mainactivity.MainActivity;

public class SearchActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActivitySearchBinding activitySearchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        bottomNavigationView = activitySearchBinding.bottomNavigationSearch;
        bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.homeView) {
                    intent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.create) {
                    intent = new Intent(SearchActivity.this, AddActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.library) {
                    intent = new Intent(SearchActivity.this, LibraryActivity.class);
                    startActivity(intent);
                    return true;
                } else return id == R.id.search;
            }
        });
    }



}