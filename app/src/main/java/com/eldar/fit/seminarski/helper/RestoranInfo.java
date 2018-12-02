package com.eldar.fit.seminarski.helper;

import com.eldar.fit.seminarski.data.TipKuhinje;
import com.eldar.fit.seminarski.data.Vlasnik;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestoranInfo implements Serializable {
    public int id;
    public String naziv;
    public String opis;
    public Vlasnik vlasnik;
    public String telefon;
    public String adresa;
    public String lokacija;
    public List<RestoranLike> lajkovi;
    public String slika;
    public String slogan;
    public String email;

    @SerializedName(value = "webUrl", alternate = {"WebUrl", "weburl"})
    public String webUrl;

    @SerializedName(value = "tipoviKuhinje", alternate = {"TipoviKuhinje", "tipovikuhinje"})
    public List<TipKuhinje> tipoviKuhinje;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Vlasnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Vlasnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public int getLikesCount() {
        return lajkovi.size();
    }


    public List<RestoranLike> getLajkovi() {
        return lajkovi;
    }

    public void setLajkovi(List<RestoranLike> lajkovi) {
        this.lajkovi = lajkovi;
    }

    public String getSlika() {
        return "http://" + slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<TipKuhinje> getTipoviKuhinje() {
        return tipoviKuhinje;
    }

    public void setTipoviKuhinje(List<TipKuhinje> tipoviKuhinje) {
        this.tipoviKuhinje = tipoviKuhinje;
    }
}
