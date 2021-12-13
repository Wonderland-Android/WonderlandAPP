package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NaviBarActivity extends AppCompatActivity {

    private BottomNavigationView btnNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_bar);

        setDefaultFragment();
        btnNavView = findViewById(R.id.bottom_navigation_menu);

        btnNavView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    System.out.println("HOME FRAGMENT");
                    replaceFragment(new Navi_barFragment());
                    return true;
                case R.id.navigation_profile:
//                    System.out.println("PROFILE FRAGMENT");
                    replaceFragment(new ProfileFragment());
                    return true;
                case R.id.navigation_settings:
//                    System.out.println("SETTINGS FRAGMENT");
                    replaceFragment(new SettingsFragment());
                    return true;
            }
            return false;
        });
    }

    private void setDefaultFragment() {
        Fragment nav_bar_fragment = new Navi_barFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, nav_bar_fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
}