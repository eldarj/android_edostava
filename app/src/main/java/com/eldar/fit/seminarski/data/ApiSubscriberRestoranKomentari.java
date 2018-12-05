package com.eldar.fit.seminarski.data;

import com.eldar.fit.seminarski.helper.RestoranRecenzija;

import java.util.List;

public class ApiSubscriberRestoranKomentari {

    public AuthLogin credentials;
    public String komentariHashCode;
    public List<RestoranRecenzija> recenzije;

    public ApiSubscriberRestoranKomentari(AuthLogin credentials, String komentariHashCode, List<RestoranRecenzija> recenzije) {
        this.credentials = credentials;
        this.komentariHashCode = komentariHashCode;
        this.recenzije = recenzije;
    }

    public String getKomentariHashCode() {
        return komentariHashCode;
    }

    public List<RestoranRecenzija> getRecenzije() {
        return recenzije;
    }
}
