package com.lta.airlock;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lta.airlock.databinding.ActivityMainBinding;

import Fragments.FrCasual;
import Fragments.FrElegant;
import Fragments.FrSports;

public class Home extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomNavigationView btnNav;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_home);
        replaceFragment(new FrElegant());
        correo = getIntent().getStringExtra("correo");

        btnNav = findViewById(R.id.bottomNavigationView);

        btnNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.elegant) {
                replaceFragment(new FrElegant());
            } else if (itemId == R.id.casual) {
                replaceFragment(new FrCasual());
            } else if (itemId == R.id.sports) {
                replaceFragment(new FrSports(correo));
            }

            return true;
        });

    }

    private void replaceFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, f);
        ft.commit();
    }
}