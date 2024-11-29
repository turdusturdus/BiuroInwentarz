package com.example.biuroinwentarz.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "serwis",
        foreignKeys = @ForeignKey(entity = Inwentarz.class,
                parentColumns = "id",
                childColumns = "id_inwentarza",
                onDelete = ForeignKey.CASCADE))
public class Serwis {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date data_serwisu;
    private String opis;

    @ColumnInfo(index = true)
    private int id_inwentarza;

    public Serwis(Date data_serwisu, String opis, int id_inwentarza) {
        this.data_serwisu = data_serwisu;
        this.opis = opis;
        this.id_inwentarza = id_inwentarza;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getData_serwisu() { return data_serwisu; }

    public void setData_serwisu(Date data_serwisu) { this.data_serwisu = data_serwisu; }

    public String getOpis() { return opis; }

    public void setOpis(String opis) { this.opis = opis; }

    public int getId_inwentarza() { return id_inwentarza; }

    public void setId_inwentarza(int id_inwentarza) { this.id_inwentarza = id_inwentarza; }
}
