package com.example.finalproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Set;

public class FavouriteDetailActivity extends AppCompatActivity {


    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);

        //Variables
        TextView titleText = findViewById(R.id.titleText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        TextView dateText = findViewById(R.id.dateText);
        Button linkButton = findViewById(R.id.linkButton);
        Button delButton = findViewById(R.id.delButton);

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

        // Delete article from favorites
        delButton.setOnClickListener(v -> {
            // Show a confirmation dialog before deleting
            new AlertDialog.Builder(FavouriteDetailActivity.this)
                    .setTitle("Delete Favorite")
                    .setMessage("Are you sure you want to delete this article from favorites?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Proceed with deletion
                            removeArticleFromFavorites(title);
                            Toast.makeText(FavouriteDetailActivity.this, "Article removed from favorites", Toast.LENGTH_SHORT).show();

                            // Restart the FavouriteActivity
                            Intent intent = new Intent(FavouriteDetailActivity.this, FavouriteActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    // Method to remove an article from SharedPreferences
    private void removeArticleFromFavorites(String titleToDelete) {
        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve the list of favorite articles (as JSON strings)
        Set<String> favoritesSet = sharedPreferences.getStringSet("fav_list", new HashSet<>());

        // Iterate through the set to find and remove the matching article
        Set<String> updatedFavoritesSet = new HashSet<>();
        for (String articleJson : favoritesSet) {
            Article article = gson.fromJson(articleJson, Article.class);
            // Add all articles except the one to be deleted
            if (!article.getTitle().equals(titleToDelete)) {
                updatedFavoritesSet.add(articleJson);
            }
        }

        // Save the updated list back to SharedPreferences
        editor.putStringSet("fav_list", updatedFavoritesSet);
        editor.apply();
    }
}
