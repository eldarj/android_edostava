package com.eldar.fit.seminarski.data;

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

    public KorisnikVM getKorisnik() {
        return korisnik;
    }
}
