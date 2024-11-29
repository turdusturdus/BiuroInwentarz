package com.example.biuroinwentarz.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.biuroinwentarz.model.Inwentarz;

import java.util.List;

@Dao
public interface InwentarzDao {

    @Insert
    void insert(Inwentarz inwentarz);

    @Update
    void update(Inwentarz inwentarz);

    @Delete
    void delete(Inwentarz inwentarz);

    @Query("SELECT * FROM inwentarz ORDER BY nazwa ASC")
    LiveData<List<Inwentarz>> getAllInwentarz();

    @Query("SELECT * FROM inwentarz WHERE id = :id")
    LiveData<Inwentarz> getInwentarzById(int id);

    @Query("SELECT * FROM inwentarz WHERE id_pomieszczenia = :id_pomieszczenia")
    LiveData<List<Inwentarz>> getInwentarzByPomieszczenie(int id_pomieszczenia);

    @Query("SELECT * FROM inwentarz WHERE id_pracownika = :id_pracownika")
    LiveData<List<Inwentarz>> getInwentarzByPracownik(int id_pracownika);
}
