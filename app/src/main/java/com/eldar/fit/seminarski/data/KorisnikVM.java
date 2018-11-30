package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KorisnikVM implements Serializable{

    @SerializedName(value="username", alternate= { "Username" })
    private String username;

    @SerializedName(value="password", alternate= { "Password" })
    private String password;

    @SerializedName(value="ime", alternate= { "Ime" })
    private String ime;

    @SerializedName(value="prezime", alternate= { "Prezime" })
    private String prezime;

    @SerializedName(value="adresa", alternate= { "Adresa" })
    private String adresa;

    private int BlokID;

    @SerializedName(value="blok", alternate= { "Blok", "BlokVm", "BlokVM", "blokVm", "blokvm" })
    private BlokVM blok;

    @SerializedName(value="imageUrl", alternate= { "ImageUrl", "imageurl", "ImageURL", "image", "Image" })
    private String imageUrl;

    public KorisnikVM(String username, String password, String ime, String prezime, String imageUrl, BlokVM blok, String adresa) {
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.blok = blok;
        this.BlokID = blok.getId();
        this.imageUrl = imageUrl;
        this.adresa = adresa;
    }
    public BlokVM getBlok(){ return blok; }

    public void setBlok(BlokVM value){ BlokID = value.getId(); blok = value; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getIme(){ return ime; }

    public void setIme(String value){ ime = value; }

    public String getPrezime(){ return prezime; }

    public void setPrezime(String value){ prezime = value; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOmiljeniRestoran() {
        return Storage.getRestorani().get(0).getNaziv();
    }

    public int getUkupnoNarudzbi() {
        return 9;
    }

    public String getDatumRegistracije() {
        return "26.9.2018";
    }

    public boolean correctLozinka(String lozinka) {
        if (this.getPassword().equals(lozinka)) {
            return true;
        }
        return false;
    }
}
