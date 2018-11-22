package com.eldar.fit.seminarski.data;

import com.eldar.fit.seminarski.helper.MySession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Korpa implements Serializable {

    private UUID uId;
    public double ukupnaCijena;
    public List<KorpaHranaStavka> hranaStavke;
    private int hranaStavkeSize;

    public Korpa() {
        uId = UUID.randomUUID();
        ukupnaCijena = 0;
        hranaStavke = new ArrayList<KorpaHranaStavka>();
    }

    public static Korpa odbaciNarudzbu() {
        Korpa korpa = new Korpa();
        MySession.setKorpa(korpa);

        return korpa;
    }

    public static Korpa izvrsiNarudzbu() {
        Korpa korpa = new Korpa();
        MySession.setKorpa(korpa);

        return korpa;
    }

    public int getHranaStavkeTotalCount() {
        int total = 0;
        for (KorpaHranaStavka s :
                hranaStavke) {
            total += s.getKolicina();
        }
        return total;
    }

    public UUID getuId() {
        return uId;
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
