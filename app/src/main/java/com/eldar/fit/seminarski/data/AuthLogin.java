package com.eldar.fit.seminarski.data;

public class AuthLogin {
    public String username;
    public String password;

    public AuthLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthLogin(KorisnikVM korisnik) {
        this.username = korisnik.getUsername();
        this.password = korisnik.getPassword();
    }
}
