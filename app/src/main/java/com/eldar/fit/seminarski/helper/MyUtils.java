package com.eldar.fit.seminarski.helper;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.eldar.fit.seminarski.LoginActivity;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MyUtils {

    public static <T> void popuniSpinner(Activity activity, List<T> podaci, int layoutId, int layoutItemId, Spinner el) {
        ArrayAdapter<T> dataAdapter = new ArrayAdapter<T>(activity, layoutId, podaci);
        dataAdapter.setDropDownViewResource(layoutItemId);
        el.setAdapter(dataAdapter);
    }

    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getApplicationWindowToken(),
                    0);
        }
    }

}
