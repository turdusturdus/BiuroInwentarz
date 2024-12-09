package com.example.biuroinwentarz.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biuroinwentarz.R;
import com.example.biuroinwentarz.databinding.FragmentPomieszczenieDetailBinding;
import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.viewmodel.PomieszczenieViewModel;

import java.util.Objects;

import android.content.Context;
import androidx.core.app.ActivityCompat;

public class PomieszczenieDetailFragment extends Fragment {

    private FragmentPomieszczenieDetailBinding binding;
    private PomieszczenieViewModel pomieszczenieViewModel;
    private int pomieszczenieId = -1;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude = 0.0;
    private double longitude = 0.0;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPomieszczenieDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pomieszczenieViewModel = new ViewModelProvider(this).get(PomieszczenieViewModel.class);

        if (getArguments() != null) {
            pomieszczenieId = getArguments().getInt("pomieszczenieId", -1);
            if (pomieszczenieId != -1) {
                binding.buttonDelete.setVisibility(View.VISIBLE);

                pomieszczenieViewModel.getPomieszczenieById(pomieszczenieId).observe(getViewLifecycleOwner(), pomieszczenie -> {
                    if (pomieszczenie != null) {
                        binding.editTextNazwaPomieszczenia.setText(pomieszczenie.getNazwa());
                        binding.editTextPietro.setText(String.valueOf(pomieszczenie.getPietro()));
                        binding.editTextPrzeznaczenie.setText(pomieszczenie.getPrzeznaczenie());
                        binding.buttonDelete.setOnClickListener(v -> deletePomieszczenie(pomieszczenie));

                        latitude = pomieszczenie.getLatitude();
                        longitude = pomieszczenie.getLongitude();
                        updateLocationDisplay();
                    }
                });
            } else {
                binding.buttonDelete.setVisibility(View.GONE);
            }
        } else {
            binding.buttonDelete.setVisibility(View.GONE);
        }

        binding.buttonSave.setOnClickListener(v -> savePomieszczenie());

        binding.buttonFetchGeolocation.setOnClickListener(v -> fetchGeolocation());

        return view;
    }

    private void fetchGeolocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    updateLocationDisplay();
                } else {
                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            updateLocationDisplay();
                            locationManager.removeUpdates(this);
                        }
                    };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } else {
                Toast.makeText(getContext(), "Brak uprawnień do pobrania lokalizacji", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateLocationDisplay() {
        binding.textViewLatitude.setText(String.format(getString(R.string.latitude), latitude));
        binding.textViewLongitude.setText(String.format(getString(R.string.longitude), longitude));
    }

    private void savePomieszczenie() {
        String nazwa = Objects.toString(binding.editTextNazwaPomieszczenia.getText(), "").trim();
        String pietroStr = Objects.toString(binding.editTextPietro.getText(), "").trim();
        String przeznaczenie = Objects.toString(binding.editTextPrzeznaczenie.getText(), "").trim();

        if (TextUtils.isEmpty(nazwa) || TextUtils.isEmpty(pietroStr)) {
            Toast.makeText(getContext(), "Proszę wprowadzić nazwę i piętro", Toast.LENGTH_SHORT).show();
            return;
        }

        int pietro;
        try {
            pietro = Integer.parseInt(pietroStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Piętro musi być liczbą całkowitą", Toast.LENGTH_SHORT).show();
            return;
        }

        Pomieszczenie pomieszczenie = new Pomieszczenie(nazwa, pietro, przeznaczenie, latitude, longitude);

        if (pomieszczenieId == -1) {
            pomieszczenieViewModel.insert(pomieszczenie);
            Toast.makeText(getContext(), "Dodano pomieszczenie", Toast.LENGTH_SHORT).show();
        } else {
            pomieszczenie.setId(pomieszczenieId);
            pomieszczenieViewModel.update(pomieszczenie);
            Toast.makeText(getContext(), "Zaktualizowano pomieszczenie", Toast.LENGTH_SHORT).show();
        }

        Navigation.findNavController(requireView()).navigateUp();
    }

    private void deletePomieszczenie(Pomieszczenie pomieszczenie) {
        pomieszczenieViewModel.delete(pomieszczenie);
        Toast.makeText(getContext(), "Usunięto pomieszczenie", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), "Brak uprawnień do pobrania lokalizacji", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
