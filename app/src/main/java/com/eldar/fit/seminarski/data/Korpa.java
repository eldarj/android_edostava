package com.eldar.fit.seminarski.data;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public KorpaHranaStavka dodajStavku(HranaItemVM stavka) {
        for (KorpaHranaStavka s :
                hranaStavke) {
            if (s.equals(stavka)) {
                s.dodajKolicinu();
                return s;
            }
        }
        KorpaHranaStavka novaStavka = new KorpaHranaStavka(stavka, 1);
        hranaStavke.add(novaStavka);
        return novaStavka;
    }

    public KorpaHranaStavka ukloniStavku(HranaItemVM stavka) {
        for (KorpaHranaStavka s :
                hranaStavke) {
            if (s.equals(stavka)) {
                if (s.getKolicina() > 1) {
                    s.smanjiKolicinu();
                    return s;
                } else {
                    hranaStavke.remove(s);
                    return null;
                }
            }
        }
        return null;
    }

    public List<KorpaHranaStavka> getHranaStavke() {
        return hranaStavke;
    }

    public KorpaHranaStavka getStavka(HranaItemVM stavka) {
        for (KorpaHranaStavka s :
                hranaStavke) {
            if (s.equals(stavka)) {
                return s;
            }
        }
        return null;
    }
}
