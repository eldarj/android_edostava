package com.eldar.fit.seminarski.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Storage {
    private static int brojacPosiljki;
    private static List<OpstinaVM> opstine;
    private static List<KorisnikVM> korisnici;
    private static List<PosiljkaVM> posiljke;

    public static List<KorisnikVM> getKorisnici(){
        if (korisnici == null) {
            korisnici = new ArrayList<KorisnikVM>();
            korisnici.add(new KorisnikVM("emina", "emina","Emina", "Obradović", getOpstine().get(0)));
            korisnici.add(new KorisnikVM("almir", "almir","Almir", "Hodžić", getOpstine().get(0)));
            korisnici.add(new KorisnikVM("anela", "anela","Anela", "Tahirović", getOpstine().get(0)));
            korisnici.add(new KorisnikVM("almasa", "almasa","Almasa", "Halilović", getOpstine().get(2)));
            korisnici.add(new KorisnikVM("armin", "armin","Armin", "Požegić", getOpstine().get(2)));
            korisnici.add(new KorisnikVM("selma", "selma","Selma", "Jasminović", getOpstine().get(4)));
            korisnici.add(new KorisnikVM("amar", "amar","Amar", "Jasminović", getOpstine().get(4)));
            korisnici.add(new KorisnikVM("selma2", "selma2","Selma", "Obradović", getOpstine().get(4)));
            korisnici.add(new KorisnikVM("eldar", "eldar","Eldar", "Jahijagić", getOpstine().get(1)));
            korisnici.add(new KorisnikVM("indira", "indira","Indira", "Jahijagić", getOpstine().get(1)));
            korisnici.add(new KorisnikVM("mirsad", "mirsad","Mirsad", "Zikić", getOpstine().get(1)));
            korisnici.add(new KorisnikVM("ensar", "ensar","Ensar", "Pohić", getOpstine().get(1)));
            korisnici.add(new KorisnikVM("alen", "alen","Alen", "Osmanović", getOpstine().get(2)));
            korisnici.add(new KorisnikVM("alija", "alija","Alija", "Gutić", getOpstine().get(2)));
        }
        return korisnici;
    }

    public static List<OpstinaVM> getOpstine(){
        if (opstine == null) {
            opstine = new ArrayList<OpstinaVM>();
            opstine.add(new OpstinaVM(1, "Mostar", "BiH"));
            opstine.add(new OpstinaVM(2, "Tuzla", "BiH"));
            opstine.add(new OpstinaVM(3, "Sarajevo", "BiH"));
            opstine.add(new OpstinaVM(4, "Banja Luka", "BiH"));
            opstine.add(new OpstinaVM(5, "Zagreb", "Hrvatska"));
        }
        return opstine;
    }

    public static List<PosiljkaVM> getPosiljke() {
        if (posiljke == null) {
            posiljke = new ArrayList<PosiljkaVM>();
            posiljke.add(new PosiljkaVM(getKorisnici().get(0), 15, 5, "Pazi lomljivo"));
            posiljke.add(new PosiljkaVM(getKorisnici().get(0), 4, 2, ""));
            posiljke.add(new PosiljkaVM(getKorisnici().get(0), 14, 4, "Lomljivo"));
            posiljke.add(new PosiljkaVM(getKorisnici().get(0), 5, 2, "Hitna isporuka"));
            posiljke.add(new PosiljkaVM(getKorisnici().get(1), 6, 2, ""));
            posiljke.add(new PosiljkaVM(getKorisnici().get(1), 1, 1, ""));
            posiljke.add(new PosiljkaVM(getKorisnici().get(1), 1, 1, "Pazi lomljivo"));
            posiljke.add(new PosiljkaVM(getKorisnici().get(1), 7, 5, ""));
            posiljke.add(new PosiljkaVM(getKorisnici().get(2), 6, 4, ""));
            posiljke.add(new PosiljkaVM(getKorisnici().get(2), 12, 7, ""));
            posiljke.add(new PosiljkaVM(getKorisnici().get(3), 13, 7, ""));
        }
        return posiljke;
    }

    public static List<KorisnikVM> getKorisniciByIme(String query){
        List<KorisnikVM> var = new ArrayList<KorisnikVM>();

        for (KorisnikVM x: getKorisnici()) {
            if ((x.getIme().toLowerCase() + " " + x.getPrezime().toLowerCase()).contains(query.toLowerCase())) {
                var.add(x);
            }
        }

        return var;
    }

    public static KorisnikVM LoginCheck(String username, String password) {
        for (KorisnikVM k: getKorisnici()) {
            if (Objects.equals(k.getUsername(), username) && Objects.equals(k.getPassword(), password)) {
                return k;
            }
        }
        return null;
    }

    public static void addKorisnik(KorisnikVM obj) {
        korisnici.add(obj);
    }

    public static List<String> getStringListOpstine(){
        List<String> result = new ArrayList<String>();
        for (OpstinaVM opstina: getOpstine()) {
            result.add(opstina.getnaziv());
        }

        return result;
    }

    public static OpstinaVM getOpstinaByPosition(int pos) {
        return getOpstine().get(pos);
    }

    public static void addPosiljka(PosiljkaVM obj) {
        posiljke.add(obj);
        obj.brojPosiljke = brojacPosiljki++;
    }

    public static void removePosiljka(PosiljkaVM obj) {
        posiljke.remove(obj);
    }

}
