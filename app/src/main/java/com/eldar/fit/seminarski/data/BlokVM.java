package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BlokVM implements Serializable {
    public static int blokIdCounter = 0;

    @SerializedName(value = "id", alternate = { "Id", "ID", "BlokID", "BlokId", "blokID", "blokId", "blokid" })
    private int id;

    @SerializedName(value = "naziv", alternate = { "Naziv" })
    private String naziv;

    @SerializedName(value = "grad", alternate = { "Grad", "Opstina", "opstina" })
    private OpstinaVM opstina;

    public BlokVM(String naziv, OpstinaVM opstina) {
        this.id = blokIdCounter++;
        this.naziv = naziv;
        this.opstina = opstina;
    }

    public int getId() {
        return id;
    }

    public String getDrzava(){ return opstina.getdrzava(); }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public OpstinaVM getOpstina() {
        return opstina;
    }

    public void setOpstina(OpstinaVM opstina) {
        this.opstina = opstina;
    }
}
