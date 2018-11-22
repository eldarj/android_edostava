package com.eldar.fit.seminarski.data;

import java.util.ArrayList;
import java.util.Arrays;
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
            korisnici.add(new KorisnikVM("emina", "emina","Emina", "Obradović", "http://ekstra.ba/assets/images/small/1.jpg", getBlokovi().get(0)));
            korisnici.add(new KorisnikVM("almir", "almir","Almir", "Hodžić", null, getBlokovi().get(0)));
            korisnici.add(new KorisnikVM("anela", "anela","Anela", "Tahirović", "http://ekstra.ba/assets/images/small/2.jpg", getBlokovi().get(0)));
            korisnici.add(new KorisnikVM("almasa", "almasa","Almasa", "Halilović","http://ekstra.ba/assets/images/small/3.jpg", getBlokovi().get(2)));
            korisnici.add(new KorisnikVM("armin", "armin","Armin", "Požegić","http://ekstra.ba/assets/images/small/4.jpg", getBlokovi().get(2)));
            korisnici.add(new KorisnikVM("selma", "selma","Selma", "Jasminović","http://ekstra.ba/assets/images/small/5.jpg", getBlokovi().get(3)));
            korisnici.add(new KorisnikVM("amar", "amar","Amar", "Jasminović","http://ekstra.ba/assets/images/small/6.jpg", getBlokovi().get(3)));
            korisnici.add(new KorisnikVM("selma2", "selma2","Selma", "Obradović","http://ekstra.ba/assets/images/small/7.jpg", getBlokovi().get(3)));
            korisnici.add(new KorisnikVM("eldar", "eldar","Eldar", "Jahijagić","http://ekstra.ba/assets/images/small/8.jpg", getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("indira", "indira","Indira", "Jahijagić","http://ekstra.ba/assets/images/small/9.jpg", getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("mirsad", "mirsad","Mirsad", "Zikić", null, getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("ensar", "ensar","Ensar", "Pohić", null, getBlokovi().get(1)));
            korisnici.add(new KorisnikVM("alen", "alen","Alen", "Osmanović", null, getBlokovi().get(2)));
            korisnici.add(new KorisnikVM("alija", "alija","Alija", "Gutić", null, getBlokovi().get(2)));
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
            restorani.add(new RestoranVM(
                    "http://p1712.app.fit.ba/uploads/images/restoran/opentable-scenic-restaurants-marine-room-FT-BLOG0818_a13b.jpg",
                    new ArrayList<String>(Arrays.asList(
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/umj3-585x390.jpg",
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/galegija-slika-image-0-02-04-db7d3d4febe34292b7070a7ee44ee6edb4c09f731c57688fe7ad438baed04a05-v-585x390.jpg",
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/galegija-slika-image-0-02-04-018fa8378a137daa454a5082a8c5c1f43023d3f9d31d78932505237d7faf8274-v-585x390.jpg",
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/galegija-slika-image-0-02-04-d02e46cc3aec2ada795f74d536fdce0e7d3f591d83ff03c7d2a3db22a3abde8b-v-585x390.jpg",
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/galegija-slika-image-0-02-04-34aec79864ea73802c315b9a0d967f7687bd8d6d0dfc38c8a3dcbe894dc67fda-v-585x390.jpg",
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/galegija-slika-image-0-02-04-7da6c994b2c87460ca6523954467f90ce43a29dc8c8c29d3aa01eb7b9f496f04-v-585x390.jpg",
                            "http://www.kibemahala.ba/wp-content/uploads/2017/08/umj4-585x390.jpg"
                    )),
                    "Petica",
                    "Tradicija 100 godina",
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
            hrana.add(new HranaItemVM("Hamburger", "Sočan hambuerger, svježe sa roštilja", null, null, getRestorani().get(0), "https://www.volim-meso.hr/wordpress/wp-content/uploads/2014/06/10-hamburger-zapovjedi.jpg", 2.50));
            hrana.add(new HranaItemVM("Tost sendvič", "Super odličan tost sendvič", null, null, getRestorani().get(0), "https://kuharica.kontin.info/wp-content/uploads/2013/11/Tost-sendvis-s-jajetom-i-senfom-10-702x467.jpg", 2.50));
            hrana.add(new HranaItemVM("Tabaskino", "Tabaskino punjen sirom, šunkom itd..", null, null, getRestorani().get(0), "https://www.theparisreview.org/blog/wp-content/uploads/2018/02/pexels-photo-660282-1024x683.jpg",3.00));
            hrana.add(new HranaItemVM("Pizza Margarita", "Najbolja i najjednostavnija", null, null, getRestorani().get(0), "http://www.tanjir.ba/thumb.php?src=image/13838454148.JPG&iar=1&fltr[]=wmi|images/art/logo.png|BL", 3));
            hrana.add(new HranaItemVM("Tuna Sendvič", "Svježa tuna i zelena salata", null, null, getRestorani().get(1),"https://www.theparisreview.org/blog/wp-content/uploads/2018/02/pexels-photo-660282-1024x683.jpg", 3));
            hrana.add(new HranaItemVM("Tabaskino", "Tabaskino je odlično jelo", null, null, getRestorani().get(1),"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf6DXTVVZ0pnxxV_XWIvU4jYcOVdhEn_cx-xFjzx9_A1eIp13I", 3.5));
            hrana.add(new HranaItemVM("Pizza Capr.", "-", null, null, getRestorani().get(1), "https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg", 3.5));
            hrana.add(new HranaItemVM("Pizza 4 vrste sira", "4 različite vrste sira", null, null, getRestorani().get(2), "https://recipes.timesofindia.com/photo/53110049.cms",3.5));
            hrana.add(new HranaItemVM("Pizza Ljutko", "Veoma ljuto", null, null, getRestorani().get(2), "https://thestingyvegan.com/wp-content/uploads/2017/10/vegan-nacho-pizza-photo.jpg", 4.5));
            hrana.add(new HranaItemVM("Ćevapi", "Direktno s roštilja", null, null, getRestorani().get(3), "http://www.tanjir.ba/thumb.php?src=image/135334772805.jpg&iar=1&fltr[]=wmi|images/art/logo.png|BL",4.5));
            hrana.add(new HranaItemVM("Pljeskavica", "Vrhunska pljeskavica pravljena sa vrhunskim mesom", null, null, getRestorani().get(3), "https://api-content.prod.pizzahutaustralia.com.au//umbraco/api/Image/Get2?path=assets/products/menu/Meat-Super-Supreme-Pizza-3250-menu.jpg",4.5));
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
