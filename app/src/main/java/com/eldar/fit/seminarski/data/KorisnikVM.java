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
    private OpstinaVM OpstinaVM;

    public KorisnikVM(String username, String password, String ime, String prezime, OpstinaVM opstinaVM) {
        Username = username;
        Password = password;
        Ime = ime;
        Prezime = prezime;
        OpstinaVM = opstinaVM;
    }
    public OpstinaVM getOpstinaVM(){ return OpstinaVM; }

    public void setOpstinaVM(OpstinaVM value){ OpstinaVM = value; }

    public String getUsername() { return Username; }

    public void setUsername(String username) { Username = username; }

    public String getPassword() { return Password; }

    public void setPassword(String password) { Password = password; }

    public String getIme(){ return Ime; }

    public void setIme(String value){ Ime = value; }

    public String getPrezime(){ return Prezime; }

    public void setPrezime(String value){ Prezime = value; }


    
}
