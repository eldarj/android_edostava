package com.eldar.fit.seminarski.helper;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

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

    public static void fragmentCreate(Activity activity, int targetLayoutId, Fragment fragment) {
        fm = activity.getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(targetLayoutId, fragment);

        if (!(fragment instanceof PosiljkaListFragment)) {
            ft.addToBackStack(null);
        }
        ft.commit();
        fragNo++;
    }

    public static void dodajDialog(Activity activity, String tag, DialogFragment dialogFragment) {
        FragmentManager fm = activity.getFragmentManager();
        dialogFragment.show(fm, tag);
    }
}
