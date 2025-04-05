package com.example.finalproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Set;

public class ArticleDetailActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        //Variables
        TextView titleText = findViewById(R.id.titleText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        TextView dateText = findViewById(R.id.dateText);
        Button linkButton = findViewById(R.id.linkButton);
        Button favButton = findViewById(R.id.favButton);

        // Get data from Intent
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        final String description = intent.getStringExtra("description");
        final String pubDate = intent.getStringExtra("pubDate");
        final String link = intent.getStringExtra("link");

        // Set TextViews
        titleText.setText(title);
        descriptionText.setText(description);
        dateText.setText(pubDate);

        // Open URL in browser
        linkButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        });

// Handle saving to favorites
        favButton.setOnClickListener(v -> {
            // Create the Article object
            Article article = new Article(title, description, pubDate, link);

            // Save the list to SharedPreferences
            saveArticletoFavourites(article);

            // Show a toast to indicate the article has been added
            Toast.makeText(ArticleDetailActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();

        });

    }

    // Method to save favorites list to SharedPreferences
    private void saveArticletoFavourites(Article article) {
        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Serialize the Article object to a JSON string
        String articleJson = gson.toJson(article);

        // Retrieve the existing favorites from SharedPreferences
        Set<String> favoritesSet = sharedPreferences.getStringSet("fav_list", new HashSet<>());

        // Create a new HashSet
        Set<String> updatedFavoritesSet = new HashSet<>(favoritesSet);

        // Add the new article to the Set
        updatedFavoritesSet.add(articleJson);

        // Save the updated Set back to SharedPreferences
        editor.putStringSet("fav_list", updatedFavoritesSet);
        editor.apply();

    }
}
