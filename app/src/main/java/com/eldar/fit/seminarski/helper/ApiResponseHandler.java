package com.eldar.fit.seminarski.helper;

import com.eldar.fit.seminarski.data.BlokPrikazVM;
import com.eldar.fit.seminarski.data.BlokVM;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseHandler {

    public static List<BlokVM> blokovi = null;

    public static List<String> getStringListBlokovi(BlokPrikazVM blokoviResponse) {
        List<String> result = new ArrayList<>();
        blokovi = blokoviResponse.blokovi;
        for (BlokVM blok: blokoviResponse.blokovi) {
            result.add(blok.getNaziv() + ", " + blok.getOpstina().getnaziv());
        }
        return result;
    }

    public static List<BlokVM> getBlokovi() {
        return blokovi;
    }
}
