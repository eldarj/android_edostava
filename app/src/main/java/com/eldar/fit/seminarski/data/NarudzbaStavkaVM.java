package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NarudzbaStavkaVM implements Serializable {
    @SerializedName(value = "naziv", alternate = { "Naziv" })
    public String naziv;

    @SerializedName(value = "cijena", alternate = { "Cijena" })
    public double cijena;

    @SerializedName(value = "kolicina", alternate = { "Kolicina" })
    public int kolicina;

    public NarudzbaStavkaVM(String naziv, double cijena, int kolicina) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.kolicina = kolicina;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
