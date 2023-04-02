package com.damskuy.petfeedermobileapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.ui.devices.DevicesFragment;
import com.damskuy.petfeedermobileapp.ui.history.HistoryFragment;
import com.damskuy.petfeedermobileapp.ui.home.HomeFragment;
import com.damskuy.petfeedermobileapp.ui.schedule.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragmentLayout(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_home) {
                replaceFragmentLayout(new HomeFragment());
            }
            if (item.getItemId() == R.id.item_history) {
                replaceFragmentLayout(new HistoryFragment());
            }
            if (item.getItemId() == R.id.item_devices) {
                replaceFragmentLayout(new DevicesFragment());
            }
            if (item.getItemId() ==R.id.item_schedule) {
                replaceFragmentLayout(new ScheduleFragment());
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
}