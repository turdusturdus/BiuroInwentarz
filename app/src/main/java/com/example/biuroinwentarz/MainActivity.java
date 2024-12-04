package com.example.biuroinwentarz;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.biuroinwentarz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment instanceof NavHostFragment) {
            NavController navController = ((NavHostFragment) navHostFragment).getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController);
        } else {
            Log.e(TAG, "NavHostFragment not found. Ensure that the fragment with ID 'nav_host_fragment' exists in the layout.");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        try {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return navController.navigateUp() || super.onSupportNavigateUp();
        } catch (IllegalStateException e) {
            Log.e(TAG, "NavController not found in onSupportNavigateUp.", e);
            return super.onSupportNavigateUp();
        }
    }
}
