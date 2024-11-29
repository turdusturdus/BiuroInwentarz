package com.example.biuroinwentarz.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.biuroinwentarz.model.Serwis;

import java.util.List;

@Dao
public interface SerwisDao {

    @Insert
    void insert(Serwis serwis);

    @Update
    void update(Serwis serwis);

    @Delete
    void delete(Serwis serwis);

    @Query("SELECT * FROM serwis ORDER BY data_serwisu DESC")
    LiveData<List<Serwis>> getAllSerwisy();

    @Query("SELECT * FROM serwis WHERE id_inwentarza = :id_inwentarza")
    LiveData<List<Serwis>> getSerwisyByInwentarz(int id_inwentarza);
}
