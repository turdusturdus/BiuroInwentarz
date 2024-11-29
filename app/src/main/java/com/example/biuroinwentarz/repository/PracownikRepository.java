package com.example.biuroinwentarz.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.dao.PracownikDao;
import com.example.biuroinwentarz.database.AppDatabase;
import com.example.biuroinwentarz.model.Pracownik;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PracownikRepository {

    private final PracownikDao pracownikDao;
    private final LiveData<List<Pracownik>> allPracownicy;
    private final ExecutorService executorService;

    public PracownikRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        pracownikDao = database.pracownikDao();
        allPracownicy = pracownikDao.getAllPracownicy();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Pracownik>> getAllPracownicy() {
        return allPracownicy;
    }

    public LiveData<Pracownik> getPracownikById(int id) {
        return pracownikDao.getPracownikById(id);
    }

    public void insert(Pracownik pracownik) {
        executorService.execute(() -> pracownikDao.insert(pracownik));
    }

    public void update(Pracownik pracownik) {
        executorService.execute(() -> pracownikDao.update(pracownik));
    }

    public void delete(Pracownik pracownik) {
        executorService.execute(() -> pracownikDao.delete(pracownik));
    }
}
