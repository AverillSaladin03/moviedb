package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.moviedb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView main_botnav_menu;
    NavHostFragment navHostFragment;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        toolbar = findViewById(R.id.main_toolbar_menu);
        setSupportActionBar(toolbar);
        main_botnav_menu = findViewById(R.id.main_botnav_menu);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navfragment_menu);

        //ToolBAR
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.nowPlayingFragment,
                R.id.topRatedFragment, R.id.upComingFragment).build();
        NavigationUI.setupActionBarWithNavController(MainMenuActivity.this,
                navHostFragment.getNavController(), appBarConfiguration);

        NavigationUI.setupWithNavController(main_botnav_menu, navHostFragment.getNavController());
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navHostFragment.getNavController().navigateUp() || super.onSupportNavigateUp();
    }
}