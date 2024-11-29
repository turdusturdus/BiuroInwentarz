package com.example.biuroinwentarz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pracownik")
public class Pracownik {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imie;
    private String nazwisko;
    private String stanowisko;
    private String email;
    private String telefon;

    public Pracownik(String imie, String nazwisko, String stanowisko, String email, String telefon) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stanowisko = stanowisko;
        this.email = email;
        this.telefon = telefon;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getImie() { return imie; }

    public void setImie(String imie) { this.imie = imie; }

    public String getNazwisko() { return nazwisko; }

    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    public String getStanowisko() { return stanowisko; }

    public void setStanowisko(String stanowisko) { this.stanowisko = stanowisko; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) { this.telefon = telefon; }
}
