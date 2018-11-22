package com.eldar.fit.seminarski.data;

import java.io.Serializable;

public class KorpaHranaStavka implements Serializable {
    private HranaItemVM hranaItemVM;
    private int kolicina;
    private double ukupnaCijena;

    public KorpaHranaStavka(HranaItemVM hranaItemVM, int kolicina) {
        this.hranaItemVM = hranaItemVM;
        this.kolicina = 1;
        ukupnaCijena = hranaItemVM.getCijena();
    }

    public HranaItemVM getHranaItemVM() {
        return hranaItemVM;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void dodajNaKolicinu() {
        ++this.kolicina;
        ukupnaCijena += hranaItemVM.getCijena();
    }

    public double getUkupnaCijena() {
        return ukupnaCijena;
    }
}
