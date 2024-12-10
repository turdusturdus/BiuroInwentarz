package com.example.biuroinwentarz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.biuroinwentarz.databinding.ActivityMainBinding;
import com.example.biuroinwentarz.work.InwentarzCheckWorker;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.time.Duration;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navView;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;

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
        }

        navView.setNavigationItemSelectedListener(this);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_dashboard,
                R.id.fragment_pomieszczenie_list,
                R.id.fragment_pracownik_list,
                R.id.fragment_inwentarz_list)
                .setDrawerLayout(drawerLayout)
                .build();

        View aboutUsView = navView.findViewById(R.id.textViewAboutUs);
        if (aboutUsView != null) {
            aboutUsView.setOnClickListener(v -> {
                String url = "https://www.android.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            });
        }

        requestNotificationPermission();
        scheduleDailyCheck();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }

    private void scheduleDailyCheck() {
        Constraints constraints = new Constraints.Builder().build();
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(InwentarzCheckWorker.class, 24, java.util.concurrent.TimeUnit.HOURS)
                .setInitialDelay(Duration.ofHours(calculateInitialDelay()))
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("inwentarzCheckWork", androidx.work.ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.withHour(12).withMinute(0).withSecond(0).withNano(0);

        if (now.isAfter(target)) {
            target = target.plusDays(1);
        }

        Duration duration = Duration.between(now, target);
        return duration.toHours();
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