package com.eldar.fit.seminarski.data;

public class HranaKategorijaVM {
    private static int hranaKategorijaIdCounter = 0;

    private int id;

    private String naziv;

    private String opis;

    public HranaKategorijaVM(String naziv, String opis) {
        this.id = hranaKategorijaIdCounter++;
        this.naziv = naziv;
        this.opis = opis;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
