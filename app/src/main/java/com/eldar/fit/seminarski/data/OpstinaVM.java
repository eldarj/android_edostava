package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asus on 11/12/2018.
 */

public class OpstinaVM implements Serializable {
    @SerializedName(value = "id", alternate = { "Id", "ID", "GradId", "GradID", "gradId", "gradID", "gradid" })
    private int id;

    @SerializedName(value = "naziv", alternate = { "Naziv" })
    private String naziv;

    @SerializedName(value = "drzava", alternate = { "Drzava" })
    private String drzava;

    @SerializedName(value = "postanskiBroj", alternate = { "PostanskiBroj", "postanskibroj" })
    private String postanskiBroj;

    public OpstinaVM(int id, String naziv, String drzava) {
        this.id = id;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public OpstinaVM(int id, String naziv, String drzava, String postanskiBroj) {
        this.id = id;
        this.naziv = naziv;
        this.drzava = drzava;
        this.postanskiBroj = postanskiBroj;
    }

    public String getdrzava(){ return drzava; }

    public void setdrzava(String value){ drzava = value; }
     
    public String getnaziv(){ return naziv; }
    
    public void setnaziv(String value){ naziv = value; }

    public int getid(){ return id; }

    public void setid(int value){ id = value; }

}
