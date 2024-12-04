package com.example.biuroinwentarz.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biuroinwentarz.R;
import com.example.biuroinwentarz.databinding.FragmentPracownikListBinding;
import com.example.biuroinwentarz.viewmodel.PracownikViewModel;

public class PracownikListFragment extends Fragment {

    private FragmentPracownikListBinding binding;
    private PracownikAdapter pracownikAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPracownikListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pracownikAdapter = new PracownikAdapter();

        binding.recyclerView.setAdapter(pracownikAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Add this line

        PracownikViewModel pracownikViewModel = new ViewModelProvider(this).get(PracownikViewModel.class);
        pracownikViewModel.getAllPracownicy().observe(getViewLifecycleOwner(), pracownicy -> pracownikAdapter.setPracownicy(pracownicy));

        binding.fabAddPracownik.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_to_pracownik_detail));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
