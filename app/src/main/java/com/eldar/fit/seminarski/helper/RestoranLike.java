package com.eldar.fit.seminarski.helper;

import com.eldar.fit.seminarski.data.KorisnikVM;

import java.io.Serializable;

public class RestoranLike implements Serializable {

    public String username;
    public String imePrezime;
    public String imageUrl;

    public String getUsername() {
        return username;
    }

    public RestoranLike(KorisnikVM k) {
        this.username = k.getUsername();
        this.imePrezime = k.getImageUrl();
        this.imageUrl = k.getImageUrl();
    }
}
