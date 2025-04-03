package com.example.finalproject;

import android.os.Bundle;

public class HeadlineActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_headline, findViewById(R.id.content_frame));
    }
}