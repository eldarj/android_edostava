package com.eldar.fit.seminarski.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PosiljkaVM implements Serializable {
    public KorisnikVM primaoc;
    public float masa;
    public String napomena;
    public int brojPosiljke;
    public float iznos;
    public Date datumSlanja;
    public boolean placaPouzecem;

    private static String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

    public PosiljkaVM(KorisnikVM primaoc, float masa, float iznos, String napomena) {
        this.datumSlanja = new Date();
        this.primaoc = primaoc;
        this.masa = masa;
        this.napomena = napomena;
        this.iznos = iznos;

        Random randomNumber = new Random();
        this.brojPosiljke = 1 + randomNumber.nextInt(100);
    }

    public String getFormattedDatum(String format) {
        String dtString;

        SimpleDateFormat formatter = format.isEmpty() ? new SimpleDateFormat(DEFAULT_DATE_FORMAT) : new SimpleDateFormat(format);
        dtString = formatter.format(datumSlanja);

        return dtString;
    }

    public PosiljkaVM() {

    }
}
