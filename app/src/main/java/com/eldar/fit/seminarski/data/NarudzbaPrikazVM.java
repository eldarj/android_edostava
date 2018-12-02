package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NarudzbaPrikazVM {
    @SerializedName(value = "narudzbe", alternate = { "Narudzbe" })
    public List<NarudzbaVM> narudzbe;
}
