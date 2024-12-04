package com.example.biuroinwentarz.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biuroinwentarz.databinding.FragmentPracownikDetailBinding;
import com.example.biuroinwentarz.model.Pracownik;
import com.example.biuroinwentarz.viewmodel.PracownikViewModel;

import java.util.Objects;

public class PracownikDetailFragment extends Fragment {

    private FragmentPracownikDetailBinding binding;
    private PracownikViewModel pracownikViewModel;
    private int pracownikId = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPracownikDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pracownikViewModel = new ViewModelProvider(this).get(PracownikViewModel.class);

        if (getArguments() != null) {
            pracownikId = getArguments().getInt("pracownikId", -1);
            if (pracownikId != -1) {
                // Edytujemy istniejącego pracownika

                // Ustawiamy przycisk usuwania jako widoczny
                binding.buttonDelete.setVisibility(View.VISIBLE);

                pracownikViewModel.getPracownikById(pracownikId).observe(getViewLifecycleOwner(), pracownik -> {
                    if (pracownik != null) {
                        binding.editTextImie.setText(pracownik.getImie());
                        binding.editTextNazwisko.setText(pracownik.getNazwisko());
                        binding.editTextStanowisko.setText(pracownik.getStanowisko());
                        binding.editTextEmail.setText(pracownik.getEmail());
                        binding.editTextTelefon.setText(pracownik.getTelefon());

                        // Ustawiamy akcję kliknięcia przycisku usuwania
                        binding.buttonDelete.setOnClickListener(v -> deletePracownik(pracownik));
                    }
                });
            } else {
                // Dodajemy nowego pracownika
                binding.buttonDelete.setVisibility(View.GONE);
            }
        } else {
            // Dodajemy nowego pracownika
            binding.buttonDelete.setVisibility(View.GONE);
        }

        binding.buttonSave.setOnClickListener(v -> savePracownik());

        return view;
    }

    private void savePracownik() {
        String imie = Objects.toString(binding.editTextImie.getText(), "").trim();
        String nazwisko = Objects.toString(binding.editTextNazwisko.getText(), "").trim();
        String stanowisko = Objects.toString(binding.editTextStanowisko.getText(), "").trim();
        String email = Objects.toString(binding.editTextEmail.getText(), "").trim();
        String telefon = Objects.toString(binding.editTextTelefon.getText(), "").trim();

        if (TextUtils.isEmpty(imie) || TextUtils.isEmpty(nazwisko)) {
            Toast.makeText(getContext(), "Proszę wprowadzić imię i nazwisko", Toast.LENGTH_SHORT).show();
            return;
        }

        Pracownik pracownik = new Pracownik(imie, nazwisko, stanowisko, email, telefon);

        if (pracownikId == -1) {
            pracownikViewModel.insert(pracownik);
            Toast.makeText(getContext(), "Dodano pracownika", Toast.LENGTH_SHORT).show();
        } else {
            pracownik.setId(pracownikId);
            pracownikViewModel.update(pracownik);
            Toast.makeText(getContext(), "Zaktualizowano pracownika", Toast.LENGTH_SHORT).show();
        }

        Navigation.findNavController(requireView()).navigateUp();
    }

    private void deletePracownik(Pracownik pracownik) {
        pracownikViewModel.delete(pracownik);
        Toast.makeText(getContext(), "Usunięto pracownika", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
