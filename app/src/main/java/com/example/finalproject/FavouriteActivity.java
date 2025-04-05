package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavouriteActivity extends BaseActivity {

    private ListView listView;
    private List<Article> favList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_favourite, findViewById(R.id.content_frame));

        listView = findViewById(R.id.favList);

        // Load favorites from SharedPreferences
        loadFavoritesFromPreferences();

        // Display the titles in ListView
        List<String> titles = new ArrayList<>();
        for (Article article : favList) {
            titles.add(article.getTitle());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        // Handle item click to show details
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Article selectedArticle = favList.get(position);

            // Start the FavouriteDetailActivity with the selected article's details
            Intent intent = new Intent(FavouriteActivity.this, FavouriteDetailActivity.class);
            intent.putExtra("title", selectedArticle.getTitle());
            intent.putExtra("description", selectedArticle.getDescription());
            intent.putExtra("pubDate", selectedArticle.getPubDate());
            intent.putExtra("link", selectedArticle.getLink());
            startActivity(intent);
        });
    }
    // Method to load favorites from SharedPreferences
    private void loadFavoritesFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
        Set<String> serializedFavorites = sharedPreferences.getStringSet("fav_list", new HashSet<>());

        // Deserialize each JSON string into an Article object
        for (String articleJson : serializedFavorites) {
            Article article = gson.fromJson(articleJson, Article.class);
            favList.add(article);
        }
    }
}