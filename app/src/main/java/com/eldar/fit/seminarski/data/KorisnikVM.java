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
    private BlokVM BlokVM;

    public KorisnikVM(String username, String password, String ime, String prezime, BlokVM blokVM) {
        Username = username;
        Password = password;
        Ime = ime;
        Prezime = prezime;
        BlokVM = blokVM;
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


    
}
