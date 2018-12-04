package com.eldar.fit.seminarski.helper;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorisnikVM;

import java.io.Serializable;

public class MyFragmentHelper {

    private static FragmentManager fm;
    private static FragmentTransaction ft;

    /*
     * FIND: "FIRST APPROACH: USING INTERFACE LISTENERS"
     */
    public interface OnKorisnikPretragaClickListener {
        void EmitKorisnikPretragaClicked(KorisnikVM korisnik);
    }

    /*
     * FIND: "SECOND APPROACH: USING INTERFACE LISTENERS"
     */
    public interface RunnableCallback<T> extends Serializable{
        void run(T t);
    }

    public static void fragmentReplace(AppCompatActivity activity, int targetLayoutId, Fragment fragment, String fragmentTag, boolean addToBackStack) {
        fm = activity.getSupportFragmentManager();
        ft = fm.beginTransaction();

        ft.replace(targetLayoutId, fragment, fragmentTag);

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.commit();
    }

    public static void dodajDialog(AppCompatActivity activity, String tag, DialogFragment dialogFragment) {
        fm = activity.getSupportFragmentManager();
        dialogFragment.show(fm, tag);
    }
}
