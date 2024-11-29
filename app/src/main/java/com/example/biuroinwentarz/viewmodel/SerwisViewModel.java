package com.example.biuroinwentarz.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.model.Serwis;
import com.example.biuroinwentarz.repository.SerwisRepository;

import java.util.List;

public class SerwisViewModel extends AndroidViewModel {

    private final SerwisRepository repository;
    private final LiveData<List<Serwis>> allSerwisy;

    public SerwisViewModel(Application application) {
        super(application);
        repository = new SerwisRepository(application);
        allSerwisy = repository.getAllSerwisy();
    }

    public LiveData<List<Serwis>> getAllSerwisy() {
        return allSerwisy;
    }

    public LiveData<List<Serwis>> getSerwisyByInwentarz(int idInwentarza) {
        return repository.getSerwisyByInwentarz(idInwentarza);
    }

    public void insert(Serwis serwis) {
        repository.insert(serwis);
    }

    public void update(Serwis serwis) {
        repository.update(serwis);
    }

    public void delete(Serwis serwis) {
        repository.delete(serwis);
    }
}
