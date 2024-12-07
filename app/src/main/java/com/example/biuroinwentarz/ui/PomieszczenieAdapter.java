package com.example.biuroinwentarz.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;

import com.example.biuroinwentarz.databinding.ItemPomieszczenieBinding;
import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.R;

import java.util.ArrayList;
import java.util.List;

public class PomieszczenieAdapter extends RecyclerView.Adapter<PomieszczenieAdapter.PomieszczenieViewHolder> {

    private List<Pomieszczenie> pomieszczenia = new ArrayList<>();

    @NonNull
    @Override
    public PomieszczenieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPomieszczenieBinding binding = ItemPomieszczenieBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PomieszczenieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PomieszczenieViewHolder holder, int position) {
        Pomieszczenie currentPomieszczenie = pomieszczenia.get(position);

        String nazwa = currentPomieszczenie.getNazwa();
        holder.binding.textViewNazwaPomieszczenia.setText(nazwa);

        String pietro = holder.itemView.getContext().getString(
                R.string.floor_number_display, currentPomieszczenie.getPietro());
        holder.binding.textViewPietro.setText(pietro);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pomieszczenieId", currentPomieszczenie.getId());
            Navigation.findNavController(v).navigate(R.id.action_to_pomieszczenie_detail, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return pomieszczenia.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPomieszczenia(List<Pomieszczenie> pomieszczenia) {
        this.pomieszczenia = pomieszczenia;
        notifyDataSetChanged();
    }

    public static class PomieszczenieViewHolder extends RecyclerView.ViewHolder {
        final ItemPomieszczenieBinding binding;

        public PomieszczenieViewHolder(ItemPomieszczenieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
