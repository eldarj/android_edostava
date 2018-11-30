package com.eldar.fit.seminarski.helper;

import com.eldar.fit.seminarski.data.TipKuhinje;
import com.eldar.fit.seminarski.data.Vlasnik;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestoranInfo {
    public int id;
    public String naziv;
    public String opis;
    public Vlasnik vlasnik;
    public String telefon;
    public String lokacija;
    public List<RestoranLike> lajkovi;
    public String slika;
    public String slogan;

    @SerializedName(value = "webUrl", alternate = {"WebUrl", "weburl"})
    public String webUrl;

    @SerializedName(value = "tipoviKuhinje", alternate = {"TipoviKuhinje", "tipovikuhinje"})
    public List<TipKuhinje> tipoviKuhinje;

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getOpis() {
        return opis;
    }

    public String getSlika() {
        return "http://" + slika;
    }

    public int getLikesCount() {
        return lajkovi.size();
    }
}
