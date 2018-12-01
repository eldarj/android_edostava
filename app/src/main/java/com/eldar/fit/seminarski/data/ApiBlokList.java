package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiBlokList {
    @SerializedName(value = "blokovi", alternate = { "Blokovi" })
    public List<BlokVM> blokovi;
}
