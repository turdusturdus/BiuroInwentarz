package com.example.biuroinwentarz.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.biuroinwentarz.databinding.FragmentInwentarzDetailBinding;
import com.example.biuroinwentarz.model.Inwentarz;
import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.utils.NotificationUtils;
import com.example.biuroinwentarz.viewmodel.InwentarzViewModel;
import com.example.biuroinwentarz.viewmodel.PomieszczenieViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class InwentarzDetailFragment extends Fragment {

    private FragmentInwentarzDetailBinding binding;
    private InwentarzViewModel inwentarzViewModel;
    private PomieszczenieViewModel pomieszczenieViewModel;
    private int inwentarzId = -1;
    private List<Pomieszczenie> pomieszczenieList;
    private int selectedRoomId = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInwentarzDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        inwentarzViewModel = new ViewModelProvider(this).get(InwentarzViewModel.class);
        pomieszczenieViewModel = new ViewModelProvider(this).get(PomieszczenieViewModel.class);

        setupRoomSpinner();

        if (getArguments() != null) {
            inwentarzId = getArguments().getInt("inwentarzId", -1);
            if (inwentarzId != -1) {
                binding.buttonDelete.setVisibility(View.VISIBLE);
                inwentarzViewModel.getInwentarzById(inwentarzId).observe(getViewLifecycleOwner(), inwentarz -> {
                    if (inwentarz != null) {
                        binding.editTextNazwa.setText(inwentarz.getNazwa());
                        binding.editTextTyp.setText(inwentarz.getTyp());
                        binding.editTextIloscObecna.setText(String.valueOf(inwentarz.getIlosc_obecna()));
                        binding.editTextIloscMin.setText(String.valueOf(inwentarz.getIlosc_min()));
                        selectedRoomId = inwentarz.getId_pomieszczenia();
                        setSpinnerSelection();
                        binding.buttonDelete.setOnClickListener(v -> deleteInwentarz(inwentarz));
                    }
                });
            } else {
                binding.buttonDelete.setVisibility(View.GONE);
            }
        } else {
            binding.buttonDelete.setVisibility(View.GONE);
        }

        binding.buttonSave.setOnClickListener(v -> saveInwentarz());

        return view;
    }

    private void setupRoomSpinner() {
        pomieszczenieList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRooms.setAdapter(adapter);

        pomieszczenieViewModel.getAllPomieszczenia().observe(getViewLifecycleOwner(), pomieszczenia -> {
            pomieszczenieList.clear();
            pomieszczenieList.addAll(pomieszczenia);
            List<String> roomNames = new ArrayList<>();
            for (Pomieszczenie room : pomieszczenia) {
                roomNames.add(room.getNazwa());
            }
            adapter.clear();
            adapter.addAll(roomNames);
            adapter.notifyDataSetChanged();
            setSpinnerSelection();
        });

        binding.spinnerRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedRoomId = pomieszczenieList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedRoomId = -1;
            }
        });
    }

    private void setSpinnerSelection() {
        if (selectedRoomId != -1 && pomieszczenieList != null) {
            for (int i = 0; i < pomieszczenieList.size(); i++) {
                if (pomieszczenieList.get(i).getId() == selectedRoomId) {
                    binding.spinnerRooms.setSelection(i);
                    break;
                }
            }
        }
    }

    private void saveInwentarz() {
        String nazwa = Objects.toString(binding.editTextNazwa.getText(), "").trim();
        String typ = Objects.toString(binding.editTextTyp.getText(), "").trim();
        String iloscObecnaStr = Objects.toString(binding.editTextIloscObecna.getText(), "").trim();
        String iloscMinStr = Objects.toString(binding.editTextIloscMin.getText(), "").trim();

        if (TextUtils.isEmpty(nazwa) || TextUtils.isEmpty(typ) || TextUtils.isEmpty(iloscObecnaStr) || TextUtils.isEmpty(iloscMinStr)) {
            Toast.makeText(getContext(), "Proszę wprowadzić wszystkie pola", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedRoomId == -1) {
            Toast.makeText(getContext(), "Proszę wybrać pomieszczenie", Toast.LENGTH_SHORT).show();
            return;
        }

        int iloscObecna;
        int iloscMin;
        try {
            iloscObecna = Integer.parseInt(iloscObecnaStr);
            iloscMin = Integer.parseInt(iloscMinStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Ilości muszą być liczbami całkowitymi", Toast.LENGTH_SHORT).show();
            return;
        }

        Inwentarz inwentarz = new Inwentarz(nazwa, typ, new Date(), null, selectedRoomId, null, iloscObecna, iloscMin);

        if (inwentarzId == -1) {
            inwentarzViewModel.insert(inwentarz);
            if (iloscObecna < iloscMin) {
                NotificationUtils.sendNotification(requireContext(), "Ilość mniejsza niż minimalna: " + nazwa);
            }
            Toast.makeText(getContext(), "Dodano przedmiot", Toast.LENGTH_SHORT).show();
        } else {
            inwentarz.setId(inwentarzId);
            inwentarzViewModel.update(inwentarz);
            if (iloscObecna < iloscMin) {
                NotificationUtils.sendNotification(requireContext(), "Ilość mniejsza niż minimalna: " + nazwa);
            }
            Toast.makeText(getContext(), "Zaktualizowano przedmiot", Toast.LENGTH_SHORT).show();
        }

        NavController navController = Navigation.findNavController(requireView());
        navController.navigateUp();
    }

    private void deleteInwentarz(Inwentarz inwentarz) {
        inwentarzViewModel.delete(inwentarz);
        Toast.makeText(getContext(), "Usunięto przedmiot", Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(requireView());
        navController.navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}