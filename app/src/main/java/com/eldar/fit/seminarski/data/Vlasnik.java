package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Vlasnik {

    @SerializedName(value = "korisnikID", alternate = {"KorisnikID", "KorisnikId", "korisnikId", "korisnikid"})
    public int korisnikID;

    public String username;
    public String password;
    public String email;

    public String ime;
    public String prezime;

    @SerializedName(value = "datumKreiranja", alternate = {"DatumKreiranja", "datumkreiranja"})
    public Date datumKreiranja;


}
