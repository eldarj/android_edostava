package com.eldar.fit.seminarski.data;

import com.eldar.fit.seminarski.helper.RestoranRecenzija;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiSubscriberRestoranKomentari {

    public AuthLogin credentials;
    public String komentariHashCode;
    @SerializedName(value = "komentari", alternate = { "Komentari", "recenzije", "Recenzije" })
    public List<RestoranRecenzija> komentari;

    public ApiSubscriberRestoranKomentari(AuthLogin credentials) {
        this.credentials = credentials;
    }

    public ApiSubscriberRestoranKomentari(AuthLogin credentials, String komentariHashCode, List<RestoranRecenzija> komentari) {
        this.credentials = credentials;
        this.komentariHashCode = komentariHashCode;
        this.komentari = komentari;
    }

    public String getKomentariHashCode() {
        return komentariHashCode;
    }

    public List<RestoranRecenzija> getKomentari() {
        return komentari;
    }
}
