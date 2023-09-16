package com.example.dht11esp8266firebasejava;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dht11esp8266firebasejava.ui.main.SectionsPagerAdapter;
//import com.example.dht11esp8266firebasejava.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        //new
        View rootView = getLayoutInflater().inflate(R.layout.activity_main2, null);
        setContentView(rootView);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //ViewPager viewPager = binding.viewPager;

        //new
        ViewPager viewPager = rootView.findViewById(R.id.view_pager);

        viewPager.setAdapter(sectionsPagerAdapter);

        Intent intent = getIntent();
        String fragmentIdentifier = intent.getStringExtra("fragment");
        if (fragmentIdentifier != null && fragmentIdentifier.equals("humidity")) {
            viewPager.setCurrentItem(2); // Assuming HumidityFragment is at index 2
        }

    }
}