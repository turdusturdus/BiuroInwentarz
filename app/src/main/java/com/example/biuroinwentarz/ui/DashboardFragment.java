package com.example.biuroinwentarz.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biuroinwentarz.R;
import com.example.biuroinwentarz.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.buttonRoomList.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_roomList));

        binding.buttonEmployeeList.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_employeeList));

        binding.buttonInventory.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_inventory));

        binding.buttonAddItem.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_addItem));

        binding.buttonAddEmployee.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_addEmployee));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
