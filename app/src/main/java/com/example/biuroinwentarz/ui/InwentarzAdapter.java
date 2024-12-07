package com.example.biuroinwentarz.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;

import com.example.biuroinwentarz.databinding.ItemInwentarzBinding;
import com.example.biuroinwentarz.model.Inwentarz;
import com.example.biuroinwentarz.R;

import java.util.ArrayList;
import java.util.List;

public class InwentarzAdapter extends RecyclerView.Adapter<InwentarzAdapter.InwentarzViewHolder> {

    private List<Inwentarz> inwentarzList = new ArrayList<>();

    @NonNull
    @Override
    public InwentarzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInwentarzBinding binding = ItemInwentarzBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new InwentarzViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InwentarzViewHolder holder, int position) {
        Inwentarz currentInwentarz = inwentarzList.get(position);

        holder.binding.textViewNazwa.setText(currentInwentarz.getNazwa());
        holder.binding.textViewTyp.setText(currentInwentarz.getTyp());

        String iloscObecna = holder.itemView.getContext().getString(
                R.string.current_quantity_display, currentInwentarz.getIlosc_obecna());
        holder.binding.textViewIloscObecna.setText(iloscObecna);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("inwentarzId", currentInwentarz.getId());
            Navigation.findNavController(v).navigate(R.id.action_to_inwentarz_detail, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inwentarzList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInwentarzList(List<Inwentarz> inwentarzList) {
        this.inwentarzList = inwentarzList;
        notifyDataSetChanged();
    }

    public static class InwentarzViewHolder extends RecyclerView.ViewHolder {
        final ItemInwentarzBinding binding;

        public InwentarzViewHolder(ItemInwentarzBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
