package com.eldar.fit.seminarski.helper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class RestoranLike implements Serializable {
    @SerializedName(value = "imePrezime", alternate = {"ImePrezime", "imeprezime"})
    public String imePrezime;
    public String recenzija;
    public Date datum;
}
