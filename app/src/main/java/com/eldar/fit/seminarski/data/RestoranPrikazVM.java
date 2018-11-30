package com.eldar.fit.seminarski.data;

import com.eldar.fit.seminarski.helper.RestoranInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestoranPrikazVM {
    @SerializedName(value = "restorani", alternate = {"Restorani"})
    public List<RestoranInfo> restorani;
}
