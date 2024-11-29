package com.example.biuroinwentarz.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.dao.PomieszczenieDao;
import com.example.biuroinwentarz.database.AppDatabase;
import com.example.biuroinwentarz.model.Pomieszczenie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PomieszczenieRepository {

    private final PomieszczenieDao pomieszczenieDao;
    private final LiveData<List<Pomieszczenie>> allPomieszczenia;
    private final ExecutorService executorService;

    public PomieszczenieRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        pomieszczenieDao = database.pomieszczenieDao();
        allPomieszczenia = pomieszczenieDao.getAllPomieszczenia();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Pomieszczenie>> getAllPomieszczenia() {
        return allPomieszczenia;
    }

    public LiveData<Pomieszczenie> getPomieszczenieById(int id) {
        return pomieszczenieDao.getPomieszczenieById(id);
    }

    public void insert(Pomieszczenie pomieszczenie) {
        executorService.execute(() -> pomieszczenieDao.insert(pomieszczenie));
    }

    public void update(Pomieszczenie pomieszczenie) {
        executorService.execute(() -> pomieszczenieDao.update(pomieszczenie));
    }

    public void delete(Pomieszczenie pomieszczenie) {
        executorService.execute(() -> pomieszczenieDao.delete(pomieszczenie));
    }
}
