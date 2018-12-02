package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NarudzbaVM implements Serializable {

    @SerializedName(value = "uId", alternate = { "uid", "guidSifra", "GuidSifra", "guidsifra"})
    private UUID uId;

    @SerializedName(value = "datumKreiranja", alternate = { "DatumKreiranja", "datumkreiranja" })
    public Date datumKreiranja;

    @SerializedName(value = "ukupnacijena", alternate = { "UkupnaCijena", "ukupnacijena" })
    public double ukupnaCijena;

    @SerializedName(value = "status", alternate = { "Status" })
    public String status;

    @SerializedName(value = "narudzbaStavke", alternate = { "HranaStavke", "hranastavke" })
    public List<NarudzbaStavkaVM> narudzbaStavke;

    @SerializedName(value = "narucenoIzRestorana", alternate = { "NarucenoIzRestorana", "narucenoizrestorana" })
    public List<String> narucenoIzRestorana;

    public UUID getuId() {
        return uId;
    }

    public Date getDatumNapravljena() {
        return datumKreiranja;
    }

    public String getDatumNapravljenaString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(datumKreiranja);
    }

    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    public String getStatus() {
        return status;
    }

    public List<NarudzbaStavkaVM> getNarudzbaStavke() {
        return narudzbaStavke;
    }

    public int getNarudzbaStavkeSize() {
        return narudzbaStavke.size();
    }

    public List<String> getNarucenoIzRestorana() {
        return narucenoIzRestorana;
    }
}
