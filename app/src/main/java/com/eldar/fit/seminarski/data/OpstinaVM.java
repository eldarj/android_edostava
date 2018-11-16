package com.eldar.fit.seminarski.data;

import java.io.Serializable;

/**
 * Created by asus on 11/12/2018.
 */

public class OpstinaVM implements Serializable {
    private int id;
    private String naziv;
    private String drzava;

    public OpstinaVM(int id, String naziv, String drzava) {
        this.id = id;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public String getdrzava(){ return drzava; }

    public void setdrzava(String value){ drzava = value; }
     
    public String getnaziv(){ return naziv; }
    
    public void setnaziv(String value){ naziv = value; }

    public int getid(){ return id; }

    public void setid(int value){ id = value; }

}
