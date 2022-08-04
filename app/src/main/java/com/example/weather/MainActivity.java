package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle actionBarDrawerToggle = null;
    private DrawerLayout drawerLayout = null;
    static public NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setStatusBarColor(getResources().getColor(R.color.backgroundColor));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.backgroundColor));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1B1B1B"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.actionbar_logo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LocationFragment()).commit();
            navigationView.setCheckedItem(R.id.LocationFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected((item))) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.WeatherFragment:
                if (!navigationView.getMenu().findItem(R.id.WeatherFragment).isChecked()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new WeatherFragment()).commit();
                }
                break;
            case R.id.LocationFragment:
                if (!navigationView.getMenu().findItem(R.id.LocationFragment).isChecked()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LocationFragment()).commit();
                }
                break;
            case R.id.ThemeFragment:
                if (!navigationView.getMenu().findItem(R.id.ThemeFragment).isChecked()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ThemeFragment()).commit();
                }
                break;
            case R.id.LanguageFragment:
                if (!navigationView.getMenu().findItem(R.id.LanguageFragment).isChecked()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LanguageFragment()).commit();
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}