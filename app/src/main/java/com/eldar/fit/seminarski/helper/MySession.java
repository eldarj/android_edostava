package com.eldar.fit.seminarski.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Korpa;

public class MySession {

    private static String SHARED_PREFS_STORAGE_NAME = "DatotekaZaSharedPreferences";
    private static String PREF_LOGGED_USER = "LoginraniUser";
    private static String PREF_KORPA = "MojaKorpa";
    private static AuthLogin authLogin;

    public static Korpa getKorpa() {
        Korpa korpa;

        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(SHARED_PREFS_STORAGE_NAME, Context.MODE_PRIVATE);

        String jsonKorpa = prefs.getString(PREF_KORPA, "");

        if (jsonKorpa.isEmpty()) {
            return null;
        }

        korpa = MyGson.build().fromJson(jsonKorpa, Korpa.class);

        return korpa;
    }


    public static void setKorpa(Korpa korpa) {
        String jsonKorpa = korpa == null ? "" : MyGson.build().toJson(korpa);

        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(SHARED_PREFS_STORAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(PREF_KORPA, jsonKorpa);
        editor.apply();
    }

    public static AuthLogin getAuthLogin() {
        if (authLogin != null) {
            return authLogin;
        }

        KorisnikVM korisnik = getKorisnik();
        if (korisnik == null) {
            return null;
        }

        authLogin = new AuthLogin(korisnik);
        return authLogin;
    }

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

    public static SharedPreferences getPrefs() {
        return MyApp.getContext().getSharedPreferences(SHARED_PREFS_STORAGE_NAME, Context.MODE_PRIVATE);
    }
}
