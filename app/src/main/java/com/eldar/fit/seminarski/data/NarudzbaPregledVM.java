package com.eldar.fit.seminarski.data;

import java.util.List;

public class NarudzbaPregledVM {
    public static class Row {
        public String userid;
        public String uuid;
        public String datumKreiranja;
        public String restorani;
        public String hrana;
        public String cijena;

        public String getUserid() {
            return userid;
        }

        public String getUuid() {
            return uuid;
        }

        public String getDatumKreiranja() {
            return datumKreiranja;
        }

        public String getRestorani() {
            return restorani;
        }

        public String getHrana() {
            return hrana;
        }

        public String getCijena() {
            return cijena;
        }
    }

    public List<Row> rows;
}
