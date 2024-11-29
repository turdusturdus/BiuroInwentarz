package com.example.biuroinwentarz.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.dao.SerwisDao;
import com.example.biuroinwentarz.database.AppDatabase;
import com.example.biuroinwentarz.model.Serwis;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerwisRepository {

    private final SerwisDao serwisDao;
    private final LiveData<List<Serwis>> allSerwisy;
    private final ExecutorService executorService;

    public SerwisRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        serwisDao = database.serwisDao();
        allSerwisy = serwisDao.getAllSerwisy();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Serwis>> getAllSerwisy() {
        return allSerwisy;
    }

    public LiveData<List<Serwis>> getSerwisyByInwentarz(int idInwentarza) {
        return serwisDao.getSerwisyByInwentarz(idInwentarza);
    }

    public void insert(Serwis serwis) {
        executorService.execute(() -> serwisDao.insert(serwis));
    }

    public void update(Serwis serwis) {
        executorService.execute(() -> serwisDao.update(serwis));
    }

    public void delete(Serwis serwis) {
        executorService.execute(() -> serwisDao.delete(serwis));
    }
}
