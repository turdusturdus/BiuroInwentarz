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

import com.example.biuroinwentarz.databinding.FragmentPomieszczenieDetailBinding;
import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.viewmodel.PomieszczenieViewModel;

import java.util.Objects;

public class PomieszczenieDetailFragment extends Fragment {

    private FragmentPomieszczenieDetailBinding binding;
    private PomieszczenieViewModel pomieszczenieViewModel;
    private int pomieszczenieId = -1;

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
                    }
                });
            } else {
                binding.buttonDelete.setVisibility(View.GONE);
            }
        } else {
            binding.buttonDelete.setVisibility(View.GONE);
        }

        binding.buttonSave.setOnClickListener(v -> savePomieszczenie());

        return view;
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

        Pomieszczenie pomieszczenie = new Pomieszczenie(nazwa, pietro, przeznaczenie);

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
