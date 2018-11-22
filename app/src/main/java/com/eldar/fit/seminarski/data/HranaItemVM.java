package com.eldar.fit.seminarski.data;

import java.io.Serializable;
import java.util.List;

public class HranaItemVM implements Serializable {
    private static int hranaItemIdCounter = 0;

    private int id;

    private String naziv;

    private String opis;

    private List<String> sastojci;

    private List<HranaKategorijaVM> kategorije;

    private RestoranVM restoran;

    private double cijena;

    private String imageUrl;

    public HranaItemVM(String naziv, String opis, List<String> sastojci, List<HranaKategorijaVM> kategorije, RestoranVM restoran, double cijena) {
        this.id = hranaItemIdCounter++;
        this.naziv = naziv;
        this.opis = opis;
        this.sastojci = sastojci;
        this.kategorije = kategorije;
        this.restoran = restoran;
        this.cijena = cijena;
        this.imageUrl = imageUrl;
    }

    public HranaItemVM(String naziv, String opis, List<String> sastojci, List<HranaKategorijaVM> kategorije, RestoranVM restoran, String imageUrl, double cijena) {
        this.id = hranaItemIdCounter++;
        this.naziv = naziv;
        this.opis = opis;
        this.sastojci = sastojci;
        this.kategorije = kategorije;
        this.restoran = restoran;
        this.cijena = cijena;
        this.imageUrl = imageUrl;
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

    public List<String> getSastojci() {
        return sastojci;
    }

    public void setSastojci(List<String> sastojci) {
        this.sastojci = sastojci;
    }

    public List<HranaKategorijaVM> getKategorije() {
        return kategorije;
    }

    public void setKategorije(List<HranaKategorijaVM> kategorije) {
        this.kategorije = kategorije;
    }

    public RestoranVM getRestoran() {
        return restoran;
    }

    public void setRestoran(RestoranVM restoran) {
        this.restoran = restoran;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }
}
