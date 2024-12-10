package com.example.biuroinwentarz.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.biuroinwentarz.R;
import com.example.biuroinwentarz.databinding.FragmentPomieszczenieDetailBinding;
import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.viewmodel.PomieszczenieViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PomieszczenieDetailFragment extends Fragment {
    private FragmentPomieszczenieDetailBinding binding;
    private PomieszczenieViewModel pomieszczenieViewModel;
    private int pomieszczenieId = -1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private Spinner spinnerPrzeznaczenie;
    private List<String> przeznaczeniaList = new ArrayList<>();
    private String currentPrzeznaczenie = null;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        getCurrentLocation();
                    } else {
                        Toast.makeText(getContext(), R.string.no_location_permission, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPomieszczenieDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        pomieszczenieViewModel = new ViewModelProvider(this).get(PomieszczenieViewModel.class);
        spinnerPrzeznaczenie = binding.spinnerPrzeznaczenie;
        fetchPrzeznaczenia("https://run.mocky.io/v3/89f257dc-1f55-4797-b6ab-eb8370570ab8");
        if (getArguments() != null) {
            pomieszczenieId = getArguments().getInt("pomieszczenieId", -1);
            if (pomieszczenieId != -1) {
                binding.buttonDelete.setVisibility(View.VISIBLE);
                pomieszczenieViewModel.getPomieszczenieById(pomieszczenieId).observe(getViewLifecycleOwner(), pomieszczenie -> {
                    if (pomieszczenie != null) {
                        binding.editTextNazwaPomieszczenia.setText(pomieszczenie.getNazwa());
                        binding.editTextPietro.setText(String.valueOf(pomieszczenie.getPietro()));
                        latitude = pomieszczenie.getLatitude();
                        longitude = pomieszczenie.getLongitude();
                        updateLocationDisplay();
                        binding.buttonDelete.setOnClickListener(v -> deletePomieszczenie(pomieszczenie));
                        currentPrzeznaczenie = pomieszczenie.getPrzeznaczenie();
                        if (przeznaczeniaList.contains(currentPrzeznaczenie)) {
                            spinnerPrzeznaczenie.setSelection(przeznaczeniaList.indexOf(currentPrzeznaczenie));
                        }
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

    private void fetchPrzeznaczenia(String urlString) {
        executorService.execute(() -> {
            List<String> fetchedPrzeznaczenia = new ArrayList<>();
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new Exception("HTTP error code: " + responseCode);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = reader.readLine()) != null) {
                    responseStrBuilder.append(inputStr);
                }

                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                JSONObject roomDestinations = jsonObject.getJSONObject("room_destinations");
                if (roomDestinations.has("pl")) {
                    for (int i = 0; i < roomDestinations.getJSONArray("pl").length(); i++) {
                        fetchedPrzeznaczenia.add(roomDestinations.getJSONArray("pl").getString(i));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            mainHandler.post(() -> {
                if (isAdded()) {
                    przeznaczeniaList.clear();
                    przeznaczeniaList.addAll(fetchedPrzeznaczenia);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, przeznaczeniaList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPrzeznaczenie.setAdapter(adapter);
                    if (currentPrzeznaczenie != null && przeznaczeniaList.contains(currentPrzeznaczenie)) {
                        spinnerPrzeznaczenie.setSelection(przeznaczeniaList.indexOf(currentPrzeznaczenie));
                    }
                }
            });
        });
    }

    private void fetchGeolocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    latitude = lastKnownLocation.getLatitude();
                    longitude = lastKnownLocation.getLongitude();
                    updateLocationDisplay();
                } else {
                    locationListener = loc -> {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                        updateLocationDisplay();
                        locationManager.removeUpdates(locationListener);
                    };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } else {
                Toast.makeText(getContext(), R.string.no_location_permission, Toast.LENGTH_SHORT).show();
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
        String przeznaczenie = spinnerPrzeznaczenie.getSelectedItem() != null ? spinnerPrzeznaczenie.getSelectedItem().toString().trim() : "";
        if (TextUtils.isEmpty(nazwa) || TextUtils.isEmpty(pietroStr)) {
            Toast.makeText(getContext(), R.string.please_provide_name_and_floor, Toast.LENGTH_SHORT).show();
            return;
        }
        int pietro;
        try {
            pietro = Integer.parseInt(pietroStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.floor_must_be_integer, Toast.LENGTH_SHORT).show();
            return;
        }
        Pomieszczenie pomieszczenie = new Pomieszczenie(nazwa, pietro, przeznaczenie, latitude, longitude);
        if (pomieszczenieId == -1) {
            pomieszczenieViewModel.insert(pomieszczenie);
            Toast.makeText(getContext(), R.string.added_room, Toast.LENGTH_SHORT).show();
        } else {
            pomieszczenie.setId(pomieszczenieId);
            pomieszczenieViewModel.update(pomieszczenie);
            Toast.makeText(getContext(), R.string.updated_room, Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(requireView()).navigateUp();
    }

    private void deletePomieszczenie(Pomieszczenie pomieszczenie) {
        pomieszczenieViewModel.delete(pomieszczenie);
        Toast.makeText(getContext(), R.string.deleted_room, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
        executorService.shutdownNow();
    }
}
