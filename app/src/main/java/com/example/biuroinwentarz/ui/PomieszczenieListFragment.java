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
import com.example.biuroinwentarz.databinding.FragmentPomieszczenieListBinding;
import com.example.biuroinwentarz.viewmodel.PomieszczenieViewModel;

public class PomieszczenieListFragment extends Fragment {

    private FragmentPomieszczenieListBinding binding;
    private PomieszczenieAdapter pomieszczenieAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPomieszczenieListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pomieszczenieAdapter = new PomieszczenieAdapter();

        binding.recyclerViewPomieszczenia.setAdapter(pomieszczenieAdapter);
        binding.recyclerViewPomieszczenia.setLayoutManager(new LinearLayoutManager(getContext()));

        PomieszczenieViewModel pomieszczenieViewModel = new ViewModelProvider(this).get(PomieszczenieViewModel.class);
        pomieszczenieViewModel.getAllPomieszczenia().observe(getViewLifecycleOwner(), pomieszczenia -> pomieszczenieAdapter.setPomieszczenia(pomieszczenia));

        binding.fabAddPomieszczenie.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_to_pomieszczenie_detail));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
