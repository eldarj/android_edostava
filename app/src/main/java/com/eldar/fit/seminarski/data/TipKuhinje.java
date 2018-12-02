package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipKuhinje implements Serializable {

    @SerializedName(value = "tipKuhinjeID", alternate = {"TipKuhinjeID", "TipKuhinjeId", "tipKuhinjeId", "tipkuhinjeid"})
    public int tipKuhinjeID;

    public String naziv;
    public String opis;

}
