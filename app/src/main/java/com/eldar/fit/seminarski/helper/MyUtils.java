package com.eldar.fit.seminarski.helper;


import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class MyUtils {

    public static <T> void popuniSpinner(Activity activity, List<T> podaci, int layoutId, int layoutItemId, Spinner el) {
        ArrayAdapter<T> dataAdapter = new ArrayAdapter<T>(activity, layoutId, podaci);
        dataAdapter.setDropDownViewResource(layoutItemId);
        el.setAdapter(dataAdapter);
    }

}
