package com.northcoders.recordshopapplication.ui.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.northcoders.recordshopapplication.ui.create.CreateActivity;

public class MainActivityClickHandler {
    Context context;

    public MainActivityClickHandler(Context context) {
        this.context = context;
    }

    public void onFABClicked(View view) {
        Intent intent = new Intent(view.getContext(), CreateActivity.class);
        context.startActivity(intent);
    }
}
