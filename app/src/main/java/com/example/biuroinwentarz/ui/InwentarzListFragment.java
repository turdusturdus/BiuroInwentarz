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

import com.example.biuroinwentarz.databinding.FragmentInwentarzListBinding;
import com.example.biuroinwentarz.viewmodel.InwentarzViewModel;
import com.example.biuroinwentarz.R;

public class InwentarzListFragment extends Fragment {

    private FragmentInwentarzListBinding binding;
    private InwentarzAdapter inwentarzAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInwentarzListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        inwentarzAdapter = new InwentarzAdapter();

        binding.recyclerViewInwentarz.setAdapter(inwentarzAdapter);
        binding.recyclerViewInwentarz.setLayoutManager(new LinearLayoutManager(getContext()));

        InwentarzViewModel inwentarzViewModel = new ViewModelProvider(this).get(InwentarzViewModel.class);
        inwentarzViewModel.getAllInwentarz().observe(getViewLifecycleOwner(), inwentarzList -> inwentarzAdapter.setInwentarzList(inwentarzList));

        binding.fabAddInwentarz.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_to_inwentarz_detail));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
