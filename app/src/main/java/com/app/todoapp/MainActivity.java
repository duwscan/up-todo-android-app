package com.app.todoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.todoapp.databinding.ActivityMainBinding;
import com.app.todoapp.fragments.IndexFragment;

public class MainActivity extends AppCompatActivity {
    final static int INDEX_FRAGMENT = R.id.index;
    final static int CALENDAR_FRAGMENT = R.id.calendar;
    final static int FOCUS_FRAGMENT = R.id.focuse;
    final static int SETTINGS_FRAGMENT = R.id.settings;
    private ActivityMainBinding binding;
    private TextView titleHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // initial fragment
        titleHeader = binding.headerTitle;
        MenuItem initialFragment = binding.bottomNavigationView.getMenu().getItem(0);
        titleHeader.setText(initialFragment.getTitle());
        replaceFragment(new IndexFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            titleHeader.setText(item.getTitle());
            if (itemId == INDEX_FRAGMENT) {
                replaceFragment(new IndexFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.commit();
    }
}