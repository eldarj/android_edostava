package com.eldar.fit.seminarski.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Storage {
    private static int brojacPosiljki;
    private static List<OpstinaVM> opstine;
    private static List<KorisnikVM> korisnici;
    private static List<PosiljkaVM> posiljke;

    private static List<RestoranVM> restorani;
    private static List<RestoranTipVM> tipoviRestorana;
    private static List<HranaKategorijaVM> kategorijeHrane;
    private static List<HranaItemVM> hrana;
    private static List<BlokVM> blokovi;


    public static List<KorisnikVM> getKorisnici(){
        if (korisnici == null) {
            korisnici = new ArrayList<KorisnikVM>();
            korisnici.add(new KorisnikVM("emina", "emina","Emina", "Obradović", getBlokovi().get(0)));
            korisnici.add(new KorisnikVM("almir", "almir","Almir", "Hodžić", getBlokovi().get(0)));
            korisnici.add(new KorisnikVM("anela", "anela","Anela", "Tahirović", getBlokovi().get(0)));
            korisnici.add(new KorisnikVM("almasa", "almasa","Almasa", "Halilović", getBlokovi().get(2)));
            korisnici.add(new KorisnikVM("armin", "armin","Armin", "Požegić", getBlokovi().get(2)));
            korisnici.add(new KorisnikVM("selma", "selma","Selma", "Jasminović", getBlokovi().get(3)));
            korisnici.add(new KorisnikVM("amar", "amar","Amar", "Jasminović", getBlokovi().get(3)));
            korisnici.add(new KorisnikVM("selma2", "selma2","Selma", "Obradović", getBlokovi().get(3)));
            korisnici.add(new KorisnikVM("eldar", "eldar","Eldar", "Jahijagić", getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("indira", "indira","Indira", "Jahijagić", getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("mirsad", "mirsad","Mirsad", "Zikić", getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("ensar", "ensar","Ensar", "Pohić", getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("alen", "alen","Alen", "Osmanović", getBlokovi().get(2)));
            korisnici.add(new KorisnikVM("alija", "alija","Alija", "Gutić", getBlokovi().get(2)));
        }
        return korisnici;
    }

    public static List<RestoranTipVM> getTipoviRestorana() {
        if (tipoviRestorana == null) {
            tipoviRestorana = new ArrayList<>();
            tipoviRestorana.add(new RestoranTipVM("Fastfood",
                    "Brza hrana (en. fast food) je termin i vrsta hrane koja se u " +
                    "restoranima brze hrane, vrlo brzo priprema i servira i usto je i jeftina pa zbog toga se naziva brza hrana"));
            tipoviRestorana.add(new RestoranTipVM("Restorani",
                    "Restorani su ugostiteljski objekti u kojem se pripremaju i poslužuju hrana i piće po izboru gosta." +
                            "Često usko vezana za tradicionalna jela, pa mogu biti talijanski, kineski itd."));
        }
        return tipoviRestorana;
    }

    public static List<HranaKategorijaVM> getKategorijeHrane() {
        if (kategorijeHrane == null) {
            kategorijeHrane = new ArrayList<>();
            kategorijeHrane.add(new HranaKategorijaVM("Sendvič", "Preukusni sendviči sačinjeni od najboljih sastojaka i namirnica."));
            kategorijeHrane.add(new HranaKategorijaVM("Hamburger", "Hamburger je vrsta sendviča u okruglom pecivu, a ista riječ može se upotrijebiti i za kosani odrezak od goveđeg mesa koji se najčešće stavlja u pecivo prilikom izrade takvog sendviča."));
            kategorijeHrane.add(new HranaKategorijaVM("Tjestenina", "Tjestenina ili pasta je skupni naziv za proizvode od rezanog svježeg ili sušenog tijesta koja se pripremaju kuhanjem."));
            kategorijeHrane.add(new HranaKategorijaVM("Roštilj", "Razna jela pripremljena s ražnja ili roštilja."));
        }
        return kategorijeHrane;
    }

    public static List<RestoranVM> getRestorani() {
        if (restorani == null) {
            restorani = new ArrayList<>();
            restorani.add(new RestoranVM("http://p1712.app.fit.ba/uploads/images/restoran/opentable-scenic-restaurants-marine-room-FT-BLOG0818_a13b.jpg",
                    null,
                    "Petica",
                    "Tradicija 100 godina",
                    null,
                    getTipoviRestorana().get(0),
                    getKorisnici().get(0),
                    "Maršala Tita 16.",
                    getBlokovi().get(3),
                    "http://p1712.app.fit.ba",
                    "petica@gmail.com",
                    "+387 62 005 152"));
            restorani.add(new RestoranVM("http://p1712.app.fit.ba/uploads/images/restoran/ITALIAN-restaurant-img_aa51.jpg",
                    null,
                    "Sezam",
                    "Tradicija 100 godina",
                    null,
                    getTipoviRestorana().get(0),
                    getKorisnici().get(1),
                    "Šabana Zah. 4",
                    getBlokovi().get(2),
                    "http://p1712.app.fit.ba",
                    "sezam@gmail.com",
                    "+387 35 121 777"));
            restorani.add(new RestoranVM("http://p1712.app.fit.ba/uploads/images/restoran/main_image_3a66.jpg",
                    null,
                    "Pizza Hut",
                    "Tradicija 100 godina",
                    null,
                    getTipoviRestorana().get(1),
                    getKorisnici().get(2),
                    "Šabana Zah. 4",
                    getBlokovi().get(0),
                    "http://p1712.app.fit.ba",
                    "pizza@hut.com",
                    "+387 62 915 911"));
            restorani.add(new RestoranVM("http://p1712.app.fit.ba/uploads/images/restoran/burgerandfriescosts_0_6fd0.jpg",
                    null,
                    "Imbis",
                    "Tradicija 50 godina",
                    null,
                    getTipoviRestorana().get(1),
                    getKorisnici().get(3),
                    "Alije Izetbegovića 19.",
                    getBlokovi().get(1),
                    "http://p1712.app.fit.ba",
                    "imbis@gmail.com",
                    "+387 62 151 599"));
            restorani.add(new RestoranVM("",
                    null,
                    "Zlatnik",
                    "Vrhunski roštilj.",
                    null,
                    getTipoviRestorana().get(1),
                    getKorisnici().get(4),
                    "Centar 31.",
                    getBlokovi().get(0),
                    "http://p1712.app.fit.ba",
                    "zlatnik@zlatnik.com",
                    "+387 61 000 000"));
        }
        return restorani;
    }

    public static List<HranaItemVM> getHrana() {
        if (hrana == null) {
            hrana = new ArrayList<>();
            hrana.add(new HranaItemVM("Hamburger", "Sočan hambuerger.", null, null, getRestorani().get(0), 2.50));
            hrana.add(new HranaItemVM("Tost sendvič", "Super odličan tost sendvič.", null, null, getRestorani().get(0), 2.50));
            hrana.add(new HranaItemVM("Tabaskino", "Tabaskino punjen sirom, šunkom itd..", null, null, getRestorani().get(0), 3.00));
            hrana.add(new HranaItemVM("Pizza Margarita", "Top margarita.", null, null, getRestorani().get(0), 3));

            hrana.add(new HranaItemVM("Hamburger", "Sočan hambuerger.", null, null, getRestorani().get(1), 3));
            hrana.add(new HranaItemVM("Tabaskino", "Tabaskino punjen sirom, šunkom itd..", null, null, getRestorani().get(1), 3.5));
            hrana.add(new HranaItemVM("Pizza Capr.", "Odlično.", null, null, getRestorani().get(1), 3.5));

            hrana.add(new HranaItemVM("Pizza 4 vrste sira", "4 različite vrste sira.", null, null, getRestorani().get(2), 3.5));
            hrana.add(new HranaItemVM("Pizza Ljutko", "Odličan ukus.", null, null, getRestorani().get(2), 4.5));

            hrana.add(new HranaItemVM("Ćevapi", "Direktno s roštlja.", null, null, getRestorani().get(3), 4.5));
            hrana.add(new HranaItemVM("Pljeskavica", "Vrhunska pljeskavica.", null, null, getRestorani().get(3), 4.5));
        }
        return hrana;
    }


    public static List<BlokVM> getBlokovi() {
        if (blokovi == null) {
            blokovi = new ArrayList<BlokVM>();
            blokovi.add(new BlokVM("Irac", getOpstine().get(1)));
            blokovi.add(new BlokVM("Brčanska Malta", getOpstine().get(1)));
            blokovi.add(new BlokVM("Centar", getOpstine().get(1)));
            blokovi.add(new BlokVM("Sjenjak", getOpstine().get(1)));
            blokovi.add(new BlokVM("Stari Grad", getOpstine().get(0)));
            blokovi.add(new BlokVM("Centar", getOpstine().get(0)));
            blokovi.add(new BlokVM("Mazoljice", getOpstine().get(0)));
            blokovi.add(new BlokVM("Skenderija", getOpstine().get(2)));
            blokovi.add(new BlokVM("Grbavica", getOpstine().get(2)));
        }
        return blokovi;
    }

    public static List<OpstinaVM> getOpstine(){
        if (opstine == null) {
            opstine = new ArrayList<OpstinaVM>();
            opstine.add(new OpstinaVM(1, "Mostar", "BiH"));
            opstine.add(new OpstinaVM(2, "Tuzla", "BiH"));
            opstine.add(new OpstinaVM(3, "Sarajevo", "BiH"));
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

    public static boolean UsernameExists(String username) {
        for (KorisnikVM k: getKorisnici()) {
            if (username.equals(k.getUsername())) {
                return true;
            }
        }
        return false;
    }


    public static List<String> getStringListOpstine(){
        List<String> result = new ArrayList<String>();
        for (OpstinaVM opstina: getOpstine()) {
            result.add(opstina.getnaziv());
        }

        return result;
    }

    public static List<String> getStringListBlokovi() {
        List<String> result = new ArrayList<>();
        for (BlokVM blok: getBlokovi()) {
            result.add(blok.getNaziv() + ", " + blok.getOpstina().getnaziv());
        }
        return result;
    }
}
