package com.eldar.fit.seminarski.data;

import android.support.annotation.Nullable;
import android.util.Log;

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
        Log.i("Test", obj instanceof HranaItemVM && this.hranaItemVM.getId() == ((HranaItemVM) obj).getId() ? "yes!" : "no...");
        return ( obj instanceof HranaItemVM && this.hranaItemVM.getId() == ((HranaItemVM) obj).getId() ) ||
                ( obj instanceof KorpaHranaStavka && this == obj );
    }

    public HranaItemVM getHranaItemVM() {
        return hranaItemVM;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void dodajKolicinu() {
        ++this.kolicina;
        ukupnaCijena += hranaItemVM.getCijena();
    }

    public void umanjiKolicinu() {
//        if (kolicina == 1) {
//
//        }
//        --this.kolicina;
//        ukupnaCijena -= hranaItemVM.getCijena();
    }

    public double getUkupnaCijena() {
        return ukupnaCijena;
    }
}
