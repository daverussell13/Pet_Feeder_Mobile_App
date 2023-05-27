package com.damskuy.petfeedermobileapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.data.auth.AuthRepository;
import com.damskuy.petfeedermobileapp.ui.devices.DevicesFragment;
import com.damskuy.petfeedermobileapp.ui.history.HistoryFragment;
import com.damskuy.petfeedermobileapp.ui.home.HomeFragment;
import com.damskuy.petfeedermobileapp.ui.login.LoginActivity;
import com.damskuy.petfeedermobileapp.ui.schedule.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initEventHandlers();
    }

    private void initUI() {
        actionBar = getSupportActionBar();
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        actionBar.setTitle("Home");
        replaceFragmentLayout(new HomeFragment());
    }

    private void initEventHandlers() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_home) {
                replaceFragmentLayout(new HomeFragment());
                actionBar.setTitle("Home");
            }
            if (item.getItemId() == R.id.item_history) {
                replaceFragmentLayout(new HistoryFragment());
                actionBar.setTitle("Feed History");
            }
            if (item.getItemId() == R.id.item_devices) {
                replaceFragmentLayout(new DevicesFragment());
                actionBar.setTitle("Devices");
            }
            if (item.getItemId() ==R.id.item_schedule) {
                replaceFragmentLayout(new ScheduleFragment());
                actionBar.setTitle("My Schedules");
            }
            return true;
        });
    }

    private void replaceFragmentLayout(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.user_profile) {
            Toast.makeText(this, "user profile clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.logout) {
            AuthRepository.getInstance().logout();
            AuthRepository.getInstance().clearUserSession(MainActivity.this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }
}