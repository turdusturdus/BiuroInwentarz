package com.example.biuroinwentarz.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.biuroinwentarz.model.Pracownik;

import java.util.List;

@Dao
public interface PracownikDao {

    @Insert
    void insert(Pracownik pracownik);

    @Update
    void update(Pracownik pracownik);

    @Delete
    void delete(Pracownik pracownik);

    @Query("SELECT * FROM pracownik ORDER BY nazwisko ASC")
    LiveData<List<Pracownik>> getAllPracownicy();

    @Query("SELECT * FROM pracownik WHERE id = :id")
    LiveData<Pracownik> getPracownikById(int id);
}
