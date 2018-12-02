package com.eldar.fit.seminarski.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HranaPrikazVM {
    @SerializedName(value = "hrana", alternate = {"Hrana"})
    public List<HranaItemVM> hrana;

}
