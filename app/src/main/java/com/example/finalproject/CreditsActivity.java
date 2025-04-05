package com.example.finalproject;

import android.os.Bundle;

public class CreditsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_credits, findViewById(R.id.content_frame));

        //Replace the container with the Gif
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new GifFragment())
                .commit();

    }
}