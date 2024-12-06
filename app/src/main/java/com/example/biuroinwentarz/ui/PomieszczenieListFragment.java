package com.example.biuroinwentarz.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biuroinwentarz.databinding.FragmentPomieszczenieListBinding;

public class PomieszczenieListFragment extends Fragment {
    private FragmentPomieszczenieListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPomieszczenieListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Setup RecyclerView, Adapter, and ViewModel here

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
