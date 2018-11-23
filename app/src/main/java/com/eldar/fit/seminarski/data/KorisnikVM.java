package com.eldar.fit.seminarski.data;

import java.io.Serializable;

/**
 * Created by asus on 11/12/2018.
 */

public class KorisnikVM implements Serializable{
    private String Username;
    private String Password;
    private String Ime;
    private String Prezime;
    private String Adresa;
    private BlokVM BlokVM;
    private String ImageUrl;

    public KorisnikVM(String username, String password, String ime, String prezime, String imageUrl, BlokVM blokVM, String adresa) {
        Username = username;
        Password = password;
        Ime = ime;
        Prezime = prezime;
        BlokVM = blokVM;
        ImageUrl = imageUrl;
        Adresa = adresa;
    }
    public BlokVM getBlokVM(){ return BlokVM; }

    public void setOpstinaVM(BlokVM value){ BlokVM = value; }

    public String getUsername() { return Username; }

    public void setUsername(String username) { Username = username; }

    public String getPassword() { return Password; }

    public void setPassword(String password) { Password = password; }

    public String getIme(){ return Ime; }

    public void setIme(String value){ Ime = value; }

    public String getPrezime(){ return Prezime; }

    public void setPrezime(String value){ Prezime = value; }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
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
}
