package com.example.biuroinwentarz.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;

import com.example.biuroinwentarz.databinding.ItemPracownikBinding;
import com.example.biuroinwentarz.model.Pracownik;
import com.example.biuroinwentarz.R;

import java.util.ArrayList;
import java.util.List;

public class PracownikAdapter extends RecyclerView.Adapter<PracownikAdapter.PracownikViewHolder> {

    private List<Pracownik> pracownicy = new ArrayList<>();

    @NonNull
    @Override
    public PracownikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPracownikBinding binding = ItemPracownikBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PracownikViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PracownikViewHolder holder, int position) {
        Pracownik currentPracownik = pracownicy.get(position);

        String fullName = holder.itemView.getContext().getString(
                R.string.full_name, currentPracownik.getImie(), currentPracownik.getNazwisko());
        holder.binding.textViewImieNazwisko.setText(fullName);

        String stanowisko = holder.itemView.getContext().getString(
                R.string.employee_position, currentPracownik.getStanowisko());
        holder.binding.textViewStanowisko.setText(stanowisko);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pracownikId", currentPracownik.getId());
            Navigation.findNavController(v).navigate(R.id.action_to_pracownik_detail, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return pracownicy.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPracownicy(List<Pracownik> pracownicy) {
        this.pracownicy = pracownicy;
        notifyDataSetChanged();
    }

    public static class PracownikViewHolder extends RecyclerView.ViewHolder {
        private final ItemPracownikBinding binding;

        public PracownikViewHolder(ItemPracownikBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
