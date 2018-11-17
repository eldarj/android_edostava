package com.eldar.fit.seminarski.data;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class RestoranVM implements Serializable {
    private static int restoranIdCounter = 0;

    private int id;

    private @Nullable String mainImageUrl;

    private @Nullable List<String> imageUrls;

    private String naziv;

    private String opis;

    private @Nullable List<HranaItemVM> hrana;

    private RestoranTipVM tipRestorana;

    private KorisnikVM vlasnik;

    private String adresa;

    private BlokVM blok;

    private @Nullable String webUrl;

    private @Nullable String email;

    private @Nullable String telefon;

    private @Nullable Boolean liked;

    private @Nullable Integer likesCount;

    private @Nullable List<KorisnikVM> korisniciLikes;

    public RestoranVM(@Nullable String mainImageUrl,
                      @Nullable List<String> imageUrls,
                      String naziv,
                      String opis,
                      @Nullable List<HranaItemVM> hrana,
                      RestoranTipVM tipRestorana,
                      KorisnikVM vlasnik,
                      String adresa,
                      BlokVM blok,
                      @Nullable String webUrl,
                      @Nullable String email,
                      @Nullable String telefon) {
        this.id = restoranIdCounter++;
        this.mainImageUrl = mainImageUrl;
        this.imageUrls = imageUrls;
        this.naziv = naziv;
        this.opis = opis;
        this.hrana = hrana;
        this.tipRestorana = tipRestorana;
        this.vlasnik = vlasnik;
        this.adresa = adresa;
        this.blok = blok;
        this.webUrl = webUrl;
        this.email = email;
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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

    public List<HranaItemVM> getHrana() {
        return hrana;
    }

    public void setHrana(List<HranaItemVM> hrana) {
        this.hrana = hrana;
    }

    public RestoranTipVM getTipRestorana() {
        return tipRestorana;
    }

    public void setTipRestorana(RestoranTipVM tipRestorana) {
        this.tipRestorana = tipRestorana;
    }

    public KorisnikVM getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(KorisnikVM vlasnik) {
        this.vlasnik = vlasnik;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public BlokVM getBlok() {
        return blok;
    }

    public void setBlok(BlokVM blok) {
        this.blok = blok;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public List<KorisnikVM> getKorisniciLikes() {
        return korisniciLikes;
    }

    public void setKorisniciLikes(List<KorisnikVM> korisniciLikes) {
        this.korisniciLikes = korisniciLikes;
    }
}
