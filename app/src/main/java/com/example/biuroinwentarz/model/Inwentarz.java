package com.example.biuroinwentarz.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "inwentarz",
        foreignKeys = {
                @ForeignKey(entity = Pomieszczenie.class,
                        parentColumns = "id",
                        childColumns = "id_pomieszczenia",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Pracownik.class,
                        parentColumns = "id",
                        childColumns = "id_pracownika",
                        onDelete = ForeignKey.SET_NULL)
        })
public class Inwentarz {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nazwa;
    private String typ;

    private Date data_dodania;

    private Date data_waznosci;

    @ColumnInfo(index = true)
    private int id_pomieszczenia;

    @ColumnInfo(index = true)
    private Integer id_pracownika;

    private int ilosc_obecna;
    private int ilosc_min;

    public Inwentarz(String nazwa, String typ, Date data_dodania, Date data_waznosci,
                     int id_pomieszczenia, Integer id_pracownika, int ilosc_obecna, int ilosc_min) {
        this.nazwa = nazwa;
        this.typ = typ;
        this.data_dodania = data_dodania;
        this.data_waznosci = data_waznosci;
        this.id_pomieszczenia = id_pomieszczenia;
        this.id_pracownika = id_pracownika;
        this.ilosc_obecna = ilosc_obecna;
        this.ilosc_min = ilosc_min;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNazwa() { return nazwa; }

    public void setNazwa(String nazwa) { this.nazwa = nazwa; }

    public String getTyp() { return typ; }

    public void setTyp(String typ) { this.typ = typ; }

    public Date getData_dodania() { return data_dodania; }

    public void setData_dodania(Date data_dodania) { this.data_dodania = data_dodania; }

    public Date getData_waznosci() { return data_waznosci; }

    public void setData_waznosci(Date data_waznosci) { this.data_waznosci = data_waznosci; }

    public int getId_pomieszczenia() { return id_pomieszczenia; }

    public void setId_pomieszczenia(int id_pomieszczenia) { this.id_pomieszczenia = id_pomieszczenia; }

    public Integer getId_pracownika() { return id_pracownika; }

    public void setId_pracownika(Integer id_pracownika) { this.id_pracownika = id_pracownika; }

    public int getIlosc_obecna() { return ilosc_obecna; }

    public void setIlosc_obecna(int ilosc_obecna) { this.ilosc_obecna = ilosc_obecna; }

    public int getIlosc_min() { return ilosc_min; }

    public void setIlosc_min(int ilosc_min) { this.ilosc_min = ilosc_min; }
}
