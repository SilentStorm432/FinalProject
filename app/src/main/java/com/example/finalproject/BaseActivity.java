package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

//Activity handles the navigation drawer so it can be used on all other activities.
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Final Project - V1.0");
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
            }
            else if (item.getItemId() == R.id.nav_headlines) {
                startActivity(new Intent(this, HeadlineActivity.class));
            }
            else if (item.getItemId() == R.id.nav_favourites) {
                startActivity(new Intent(this, FavouriteActivity.class));
            }
            else if (item.getItemId() == R.id.nav_credits) {
                startActivity(new Intent(this, CreditsActivity.class));
            }
            else if (item.getItemId() == R.id.nav_exit) {
                finishAffinity();
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_help) {
            new AlertDialog.Builder(this)
                    .setTitle("Help")
                    .setMessage("You can select your desired page from the drop-down menu on the top left.\n\nOn the headlines page, you can select a headline and either open it or add it to your favourites.\n\nOn the favourites page, select a headline to either open it or delete it from favourites.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // This will close the dialog
                        }
                    })
                    .show();

        }
        return true;
    }
}