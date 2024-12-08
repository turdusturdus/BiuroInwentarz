package com.example.biuroinwentarz;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.biuroinwentarz.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        drawerLayout = binding.drawerLayout;
        navView = binding.navigationView;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        } else {
            throw new IllegalStateException("NavHostFragment not found");
        }

        navView.setNavigationItemSelectedListener(this);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_dashboard,
                R.id.fragment_pomieszczenie_list,
                R.id.fragment_pracownik_list,
                R.id.fragment_inwentarz_list)
                .setDrawerLayout(drawerLayout)
                .build();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_dashboard) {
            navController.navigate(R.id.fragment_dashboard);
        } else if (itemId == R.id.nav_room_list) {
            navController.navigate(R.id.fragment_pomieszczenie_list);
        } else if (itemId == R.id.nav_employee_list) {
            navController.navigate(R.id.fragment_pracownik_list);
        } else if (itemId == R.id.nav_inventory) {
            navController.navigate(R.id.fragment_inwentarz_list);
        }

        drawerLayout.closeDrawer(navView);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
