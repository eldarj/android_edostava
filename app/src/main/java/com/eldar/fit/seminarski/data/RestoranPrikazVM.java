package com.eldar.fit.seminarski.data;

import com.eldar.fit.seminarski.helper.MySession;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RestoranPrikazVM {
    @SerializedName(value = "restorani", alternate = {"Restorani"})
    public List<RestoranInfo> restorani;

    public List<RestoranInfo> omiljeniRestorani;

    public List<RestoranInfo> getRestorani() {
        return restorani;
    }

    public List<RestoranInfo> getOmiljeniRestorani() {
        if (omiljeniRestorani != null) {
            return omiljeniRestorani;
        }
        RestoranLike userLike = new RestoranLike(MySession.getKorisnik());
        omiljeniRestorani = new ArrayList<>();
        for (RestoranInfo r : restorani) {
            if (r.userHasLiked()) {
                omiljeniRestorani.add(r);
            }
        }
        return omiljeniRestorani;
    }
}
