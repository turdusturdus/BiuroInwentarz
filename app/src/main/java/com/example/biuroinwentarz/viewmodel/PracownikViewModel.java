package com.example.biuroinwentarz.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.model.Pracownik;
import com.example.biuroinwentarz.repository.PracownikRepository;

import java.util.List;

public class PracownikViewModel extends AndroidViewModel {

    private final PracownikRepository repository;
    private final LiveData<List<Pracownik>> allPracownicy;

    public PracownikViewModel(Application application) {
        super(application);
        repository = new PracownikRepository(application);
        allPracownicy = repository.getAllPracownicy();
    }

    public LiveData<List<Pracownik>> getAllPracownicy() {
        return allPracownicy;
    }

    public LiveData<Pracownik> getPracownikById(int id) {
        return repository.getPracownikById(id);
    }

    public void insert(Pracownik pracownik) {
        repository.insert(pracownik);
    }

    public void update(Pracownik pracownik) {
        repository.update(pracownik);
    }

    public void delete(Pracownik pracownik) {
        repository.delete(pracownik);
    }
}
