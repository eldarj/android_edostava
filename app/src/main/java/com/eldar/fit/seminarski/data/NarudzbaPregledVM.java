package com.eldar.fit.seminarski.data;

import java.util.List;

public class NarudzbePregledVM {
    public static class Row {
        public String userid;
        public String uuid;
        public String datumKreiranja;
        public String restorani;
        public String hrana;
        public String cijena;
    }

    public List<Row> rows;
}
