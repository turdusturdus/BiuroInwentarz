package com.example.biuroinwentarz.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.biuroinwentarz.model.Pomieszczenie;

import java.util.List;

@Dao
public interface PomieszczenieDao {

    @Insert
    void insert(Pomieszczenie pomieszczenie);

    @Update
    void update(Pomieszczenie pomieszczenie);

    @Delete
    void delete(Pomieszczenie pomieszczenie);

    @Query("SELECT * FROM pomieszczenie ORDER BY nazwa ASC")
    LiveData<List<Pomieszczenie>> getAllPomieszczenia();

    @Query("SELECT * FROM pomieszczenie WHERE id = :id")
    LiveData<Pomieszczenie> getPomieszczenieById(int id);
}
