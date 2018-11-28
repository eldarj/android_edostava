package com.eldar.fit.seminarski.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NarudzbaVM {

    private UUID uId;
    public List<KorpaHranaStavka> hranaStavke;
    private int hranaStavkeSize;
    public double ukupnaCijena;
    public Date datumNapravljena;
    public KorisnikVM korisnik;

    public NarudzbaVM(UUID uId, List<KorpaHranaStavka> hranaStavke, double ukupnaCijena, KorisnikVM korisnik) {
        this.uId = uId;
        this.hranaStavke = hranaStavke;
        this.ukupnaCijena = ukupnaCijena;
        this.korisnik = korisnik;
        this.hranaStavkeSize = hranaStavke.size();
        this.datumNapravljena = new Date();
    }

    public UUID getuId() {
        return uId;
    }

    public List<KorpaHranaStavka> getHranaStavke() {
        return hranaStavke;
    }

    public int getHranaStavkeSize() {
        return hranaStavke.size();
    }

    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    public Date getDatumNapravljena() {
        return datumNapravljena;
    }

    public String getDatumNapravljenaString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(getDatumNapravljena());
    }

    public KorisnikVM getKorisnik() {
        return korisnik;
    }

    public List<RestoranVM> getRestorani() {
        List<RestoranVM> restorani = new ArrayList<RestoranVM>();
        for (KorpaHranaStavka stavka:
             hranaStavke) {
            restorani.add(stavka.getHranaItemVM().getRestoran());
        }
        return restorani;
    }
}
