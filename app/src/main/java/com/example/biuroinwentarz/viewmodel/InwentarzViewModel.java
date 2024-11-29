package com.example.biuroinwentarz.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.model.Inwentarz;
import com.example.biuroinwentarz.repository.InwentarzRepository;

import java.util.List;

public class InwentarzViewModel extends AndroidViewModel {

    private final InwentarzRepository repository;
    private final LiveData<List<Inwentarz>> allInwentarz;

    public InwentarzViewModel(Application application) {
        super(application);
        repository = new InwentarzRepository(application);
        allInwentarz = repository.getAllInwentarz();
    }

    public LiveData<List<Inwentarz>> getAllInwentarz() {
        return allInwentarz;
    }

    public LiveData<Inwentarz> getInwentarzById(int id) {
        return repository.getInwentarzById(id);
    }

    public LiveData<List<Inwentarz>> getInwentarzByPomieszczenie(int idPomieszczenia) {
        return repository.getInwentarzByPomieszczenie(idPomieszczenia);
    }

    public LiveData<List<Inwentarz>> getInwentarzByPracownik(int idPracownika) {
        return repository.getInwentarzByPracownik(idPracownika);
    }

    public void insert(Inwentarz inwentarz) {
        repository.insert(inwentarz);
    }

    public void update(Inwentarz inwentarz) {
        repository.update(inwentarz);
    }

    public void delete(Inwentarz inwentarz) {
        repository.delete(inwentarz);
    }
}
