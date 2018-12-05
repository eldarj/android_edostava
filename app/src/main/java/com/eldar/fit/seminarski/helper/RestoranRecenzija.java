package com.eldar.fit.seminarski.helper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestoranRecenzija implements Serializable {
    @SerializedName(value = "imePrezime", alternate = {"ImePrezime", "imeprezime"})
    public String imePrezime;
    public String username;
    public String imageUrl;
    public String recenzija;
    public boolean liked;
    public Date datum;

    public String getImePrezime() {
        return imePrezime;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrlForDisplay() {
        return MyApiConfig.APP_BASE + imageUrl;
    }

    public String getRecenzija() {
        return recenzija;
    }

    public String getDatumString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(datum);
    }
}
