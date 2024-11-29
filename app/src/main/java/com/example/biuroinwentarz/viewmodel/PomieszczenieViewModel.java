package com.example.biuroinwentarz.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.repository.PomieszczenieRepository;

import java.util.List;

public class PomieszczenieViewModel extends AndroidViewModel {

    private final PomieszczenieRepository repository;
    private final LiveData<List<Pomieszczenie>> allPomieszczenia;

    public PomieszczenieViewModel(Application application) {
        super(application);
        repository = new PomieszczenieRepository(application);
        allPomieszczenia = repository.getAllPomieszczenia();
    }

    public LiveData<List<Pomieszczenie>> getAllPomieszczenia() {
        return allPomieszczenia;
    }

    public LiveData<Pomieszczenie> getPomieszczenieById(int id) {
        return repository.getPomieszczenieById(id);
    }

    public void insert(Pomieszczenie pomieszczenie) {
        repository.insert(pomieszczenie);
    }

    public void update(Pomieszczenie pomieszczenie) {
        repository.update(pomieszczenie);
    }

    public void delete(Pomieszczenie pomieszczenie) {
        repository.delete(pomieszczenie);
    }
}
