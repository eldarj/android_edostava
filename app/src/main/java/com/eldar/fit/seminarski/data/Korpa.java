package com.eldar.fit.seminarski.data;

import com.eldar.fit.seminarski.helper.MySession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Korpa implements Serializable {

    public double ukupnaCijena;
    public List<KorpaHranaStavka> hranaStavke;

    public Korpa() {
        ukupnaCijena = 0;
        hranaStavke = new ArrayList<KorpaHranaStavka>();
    }

    public int getHranaStavkeTotalCount() {
        int total = 0;
        for (KorpaHranaStavka s :
                hranaStavke) {
            total += s.getKolicina();
        }
        return total;
    }

    public double getUkupnaCijena() {
        ukupnaCijena = 0;
        for (KorpaHranaStavka k :
                hranaStavke) {
            ukupnaCijena += (double) k.getUkupnaCijena();
        }
        return ukupnaCijena;
    }

    public void dodajStavku(KorpaHranaStavka stavka) {
        for (KorpaHranaStavka s :
                hranaStavke) {
            if (s.equals(stavka)) {
                s.dodajKolicinu();
                return;
            }
        }
        hranaStavke.add(stavka);
    }

    public void dodajStavku(HranaItemVM stavka) {
        for (KorpaHranaStavka s :
                hranaStavke) {
            if (s.equals(stavka)) {
                s.dodajKolicinu();
                return;
            }
        }
        hranaStavke.add(new KorpaHranaStavka(stavka, 1));
    }

    public void setUkupnaCijena(double ukupnaCijena) {
        this.ukupnaCijena = ukupnaCijena;
    }

    public List<KorpaHranaStavka> getHranaStavke() {
        return hranaStavke;
    }

    public void setHranaStavke(List<KorpaHranaStavka> hranaStavke) {
        this.hranaStavke = hranaStavke;
    }
}
