package com.example.biuroinwentarz.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biuroinwentarz.R;
import com.example.biuroinwentarz.databinding.FragmentPracownikListBinding;
import com.example.biuroinwentarz.model.Pracownik;
import com.example.biuroinwentarz.viewmodel.PracownikViewModel;

import java.util.List;

public class PracownikListFragment extends Fragment {

    private FragmentPracownikListBinding binding;
    private PracownikAdapter pracownikAdapter;
    private PracownikViewModel pracownikViewModel;

    private Observer<List<Pracownik>> pracownicyObserver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPracownikListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pracownikAdapter = new PracownikAdapter();
        binding.recyclerView.setAdapter(pracownikAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pracownikViewModel = new ViewModelProvider(this).get(PracownikViewModel.class);

        pracownicyObserver = pracownicy -> pracownikAdapter.setPracownicy(pracownicy);

        // Początkowe zaobserwowanie wszystkich pracowników
        pracownikViewModel.getAllPracownicy().observe(getViewLifecycleOwner(), pracownicyObserver);

        binding.fabAddPracownik.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_to_pracownik_detail));

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }

            private void performSearch(String query) {
                // Usunięcie obecnego obserwatora
                pracownikViewModel.getAllPracownicy().removeObserver(pracownicyObserver);
                pracownikViewModel.getPracownicyByNazwisko(query).removeObserver(pracownicyObserver);

                if (query == null || query.trim().isEmpty()) {
                    // Jeśli zapytanie jest puste, zaobserwuj wszystkich pracowników
                    pracownikViewModel.getAllPracownicy().observe(getViewLifecycleOwner(), pracownicyObserver);
                } else {
                    // W przeciwnym razie, zaobserwuj pracowników filtrujących się po nazwisku
                    pracownikViewModel.getPracownicyByNazwisko(query.trim()).observe(getViewLifecycleOwner(), pracownicyObserver);
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
