package com.eldar.fit.seminarski.data;

import android.support.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.hranaItemVM == obj || this == obj;
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
