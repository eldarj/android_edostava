package com.eldar.fit.seminarski.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.eldar.fit.seminarski.data.KorisnikVM;

public class MySession {

    private static String SHARED_PREFS_STORAGE_NAME = "DatotekaZaSharedPreferences";
    private static String PREF_LOGGED_USER = "LoginraniUser";

    public static KorisnikVM getKorisnik() {
        KorisnikVM korisnik;

        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(SHARED_PREFS_STORAGE_NAME, Context.MODE_PRIVATE);

        String jsonKorisnik = prefs.getString(PREF_LOGGED_USER, "");

        if (jsonKorisnik.isEmpty()) {
            return null;
        }

        korisnik = MyGson.build().fromJson(jsonKorisnik, KorisnikVM.class);

        return korisnik;
    }

    public static void setKorisnik(KorisnikVM korisnik) {
        String jsonKorisnik = korisnik == null ? "" : MyGson.build().toJson(korisnik);

        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(SHARED_PREFS_STORAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(PREF_LOGGED_USER, jsonKorisnik);
        editor.apply();
    }
}
