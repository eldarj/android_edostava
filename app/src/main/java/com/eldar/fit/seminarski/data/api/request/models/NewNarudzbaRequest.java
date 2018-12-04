package com.eldar.fit.seminarski.data.api.request.models;

import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.Korpa;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewNarudzbaRequest implements Serializable {

    private class StavkaRequest
    {
        public int hranaID;
        public int kolicina;

        public StavkaRequest(int hranaID, int kolicina) {
            this.hranaID = hranaID;
            this.kolicina = kolicina;
        }
    }

    public AuthLogin credentials;

    public double ukupnaCijena;

    public List<StavkaRequest> stavke;

    public NewNarudzbaRequest(AuthLogin credentials, double ukupnaCijena, List<StavkaRequest> stavke) {
        this.credentials = credentials;
        this.ukupnaCijena = ukupnaCijena;
        this.stavke = stavke;
    }

    public NewNarudzbaRequest(AuthLogin credentials, Korpa korpa) {
        this.credentials = credentials;
        this.ukupnaCijena = korpa.getUkupnaCijena();
        this.stavke = new ArrayList<>();
        for (KorpaHranaStavka s :
                korpa.getHranaStavke()) {
            stavke.add(new StavkaRequest(s.getHranaItemVM().getId(), s.getKolicina()));
        }
    }

}
