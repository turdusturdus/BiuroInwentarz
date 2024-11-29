package com.example.biuroinwentarz.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.biuroinwentarz.dao.InwentarzDao;
import com.example.biuroinwentarz.database.AppDatabase;
import com.example.biuroinwentarz.model.Inwentarz;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InwentarzRepository {

    private final InwentarzDao inwentarzDao;
    private final LiveData<List<Inwentarz>> allInwentarz;
    private final ExecutorService executorService;

    public InwentarzRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        inwentarzDao = database.inwentarzDao();
        allInwentarz = inwentarzDao.getAllInwentarz();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Inwentarz>> getAllInwentarz() {
        return allInwentarz;
    }

    public LiveData<Inwentarz> getInwentarzById(int id) {
        return inwentarzDao.getInwentarzById(id);
    }

    public LiveData<List<Inwentarz>> getInwentarzByPomieszczenie(int idPomieszczenia) {
        return inwentarzDao.getInwentarzByPomieszczenie(idPomieszczenia);
    }

    public LiveData<List<Inwentarz>> getInwentarzByPracownik(int idPracownika) {
        return inwentarzDao.getInwentarzByPracownik(idPracownika);
    }

    public void insert(Inwentarz inwentarz) {
        executorService.execute(() -> inwentarzDao.insert(inwentarz));
    }

    public void update(Inwentarz inwentarz) {
        executorService.execute(() -> inwentarzDao.update(inwentarz));
    }

    public void delete(Inwentarz inwentarz) {
        executorService.execute(() -> inwentarzDao.delete(inwentarz));
    }
}
