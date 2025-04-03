package com.example.finalproject;

import android.os.Bundle;

public class FavouriteActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_favourite, findViewById(R.id.content_frame));
    }
}