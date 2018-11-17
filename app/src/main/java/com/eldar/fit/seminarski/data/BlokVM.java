package com.eldar.fit.seminarski.data;

import java.io.Serializable;

public class BlokVM implements Serializable {
    public static int blokIdCounter = 0;

    private int id;

    private String naziv;

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
