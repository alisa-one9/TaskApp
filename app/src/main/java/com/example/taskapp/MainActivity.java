package com.example.taskapp;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNav();
        navController.navigate(R.id.boardFragment);

    }
    private  void initNav(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
                appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            if(destination.getId()==R.id.navigation_home||
            destination.getId()==R.id.navigation_notifications||
            destination.getId()==R.id.navigation_dashboard){
                navView.setVisibility(View.VISIBLE);

            } else{navView.setVisibility(View.GONE);}

            if(destination.getId()==R.id.boardFragment) {
                getSupportActionBar().hide();

            } else {
                getSupportActionBar().show();
            }
        });
    };

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) ||
                super.onSupportNavigateUp();
    }
}