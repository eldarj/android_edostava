package com.eldar.fit.seminarski.data;

public class NewKomentarRequest {

    private AuthLogin credentials;

    private String komentar;

    public NewKomentarRequest(AuthLogin credentials, String komentar) {
        this.credentials = credentials;
        this.komentar = komentar;
    }
}
