package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

public class TipKuhinje {

    @SerializedName(value = "tipKuhinjeID", alternate = {"TipKuhinjeID", "TipKuhinjeId", "tipKuhinjeId", "tipkuhinjeid"})
    public int tipKuhinjeID;

    public String naziv;
    public String opis;

}
