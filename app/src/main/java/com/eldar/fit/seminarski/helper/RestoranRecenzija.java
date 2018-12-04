package com.eldar.fit.seminarski.helper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class RestoranRecenzija implements Serializable {
    @SerializedName(value = "imePrezime", alternate = {"ImePrezime", "imeprezime"})
    public String imePrezime;
    public String imageUrl;
    public String recenzija;
    public boolean liked;
    public Date datum;
}
