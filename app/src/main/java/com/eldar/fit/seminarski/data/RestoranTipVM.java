package com.eldar.fit.seminarski.data;

public class RestoranTipVM {
    private static int tipRestoranaIdCounter = 0;

    private int id;

    private String naziv;

    private String opis;

    public RestoranTipVM(String naziv, String opis)  {
        this.id = tipRestoranaIdCounter++;
        this.naziv = naziv;
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
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
}
