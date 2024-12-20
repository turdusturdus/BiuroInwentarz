package com.example.biuroinwentarz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pomieszczenie")
public class Pomieszczenie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nazwa;
    private int pietro;
    private String przeznaczenie;
    private double latitude;
    private double longitude;

    public Pomieszczenie(String nazwa, int pietro, String przeznaczenie, double latitude, double longitude) {
        this.nazwa = nazwa;
        this.pietro = pietro;
        this.przeznaczenie = przeznaczenie;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }

    public int getPietro() { return pietro; }
    public void setPietro(int pietro) { this.pietro = pietro; }

    public String getPrzeznaczenie() { return przeznaczenie; }
    public void setPrzeznaczenie(String przeznaczenie) { this.przeznaczenie = przeznaczenie; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}