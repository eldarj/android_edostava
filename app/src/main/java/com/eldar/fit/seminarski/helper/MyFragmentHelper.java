package com.eldar.fit.seminarski.helper;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.fragments.PosiljkaListFragment;

import java.io.Serializable;

public class MyFragmentHelper {

    private static int fragNo = 0;
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

    public static void fragmentEmptyAndCreate(AppCompatActivity activity, int targetLayoutId, Fragment fragment) {

    }

    public static void fragmentCreate(AppCompatActivity activity, int targetLayoutId, Fragment fragment) {
        fm = activity.getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(targetLayoutId, fragment);

        if (!(fragment instanceof PosiljkaListFragment)) {
            ft.addToBackStack(null);
        }

        Log.i("Test:", "fragmentCreate()");

        ft.commit();
        fragNo++;
    }

    public static void dodajDialog(AppCompatActivity activity, String tag, DialogFragment dialogFragment) {
        FragmentManager fm = activity.getSupportFragmentManager();
        dialogFragment.show(fm, tag);
    }
}
