package com.example.biuroinwentarz.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.biuroinwentarz.dao.PracownikDao;
import com.example.biuroinwentarz.dao.PomieszczenieDao;
import com.example.biuroinwentarz.dao.InwentarzDao;
import com.example.biuroinwentarz.dao.SerwisDao;
import com.example.biuroinwentarz.model.Pracownik;
import com.example.biuroinwentarz.model.Pomieszczenie;
import com.example.biuroinwentarz.model.Inwentarz;
import com.example.biuroinwentarz.model.Serwis;
import com.example.biuroinwentarz.model.Converters;

@Database(entities = {Pracownik.class, Pomieszczenie.class, Inwentarz.class, Serwis.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract PracownikDao pracownikDao();
    public abstract PomieszczenieDao pomieszczenieDao();
    public abstract InwentarzDao inwentarzDao();
    public abstract SerwisDao serwisDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
