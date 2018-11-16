package com.eldar.fit.seminarski.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;

import java.util.List;

public class KorisnikPretragaDialogFragment extends DialogFragment {

    private SearchView searchViewPretraga;
    private Button btnNoviKorisnik;
    private ImageView btnClose;
    private ListView listKorisnici;
    private BaseAdapter listKorisniciAdapter;

    private List<KorisnikVM> podaci;

    // Listeners
    private MyFragmentHelper.OnKorisnikPretragaClickListener KorisnikPretragaEmitter; // FIRST APPROACH
    private static MyFragmentHelper.RunnableCallback<KorisnikVM> callback; // SECOND APPROACH
    private static String BUNDLE_KORISNIK_PRETRAGA_CALLBACK = "bundle_korisnik_pretraga_callback"; // SECOND APPROACH

    public static KorisnikPretragaDialogFragment newInstance(MyFragmentHelper.RunnableCallback callback) {
        KorisnikPretragaDialogFragment fragment = new KorisnikPretragaDialogFragment();

        // SECOND APPROACH:
        // Možemo direktno izdvojiti callback (odnosno naš listener-parent fragment) objekat
        // fragment.callback = callback;
        // ili da koristimo bundle
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KORISNIK_PRETRAGA_CALLBACK, callback);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SECOND APPROACH: Ako koristimo bundle za callback, preuzimamo ga ovdje pa onda dodjelimo callback
        if (getArguments().containsKey(BUNDLE_KORISNIK_PRETRAGA_CALLBACK)) {
            callback = (MyFragmentHelper.RunnableCallback) getArguments().getSerializable(BUNDLE_KORISNIK_PRETRAGA_CALLBACK);
        }

        setStyle(STYLE_NORMAL, R.style.DialogsTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_korisnik_pretraga, container, false);

        searchViewPretraga = view.findViewById(R.id.searchViewPretraga);
        searchViewPretraga.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                do_PretraziPrimaoce(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                do_PretraziPrimaoce(s);
                return false;
            }
        });

        listKorisnici = view.findViewById(R.id.listViewPretraga);
        listKorisnici.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                KorisnikVM korisnik = Storage.getKorisnici().get(i);

                /*
                  * FIRST APPROACH: USING INTERFACE LISTENERS
                  * DOWNSIDE: WHEN DEALING WITH MULTIPLE FRAGMENTS, ACTIVITY HAS TO BE THE LISTENER AND THEN
                  *           - IT HAS TO REFRESH THE OTHER FRAGMENT THAT WE WANT TO UPDATE,
                  *           - BY RECREATING IT AND PASSING ARGS BY BUNDLE.
                  * EXECUTION CODE: KorisnikPretragaEmitter.EmitKorisnikPretragaClicked(korisnik);
                  * REQUIRES: GET THE LISTENER CONTEXT (IN THIS CASE ACTIVITY) ie. IMPLEMENT onAttach
                  *           - DEFINE THE INTERFACE LISTENER EMITTER IN FRAGMENT
                  *           - ACTIVITY IMPLEMENTS INTERFACE AND OVERIDES THE EMIT METHOD
                 */
                // KorisnikPretragaEmitter.EmitKorisnikPretragaClicked(korisnik);


                /*
                  * SECOND APPROACH: USING A CUSTOM RUNNABLE INTERFACE, WHICH WE THEN USE BY PASSING IT FROM LISTENER FRAGMENT TO EMITTER FRAGMENT
                  * UPSIDE:   DOESN'T REQUIRE FETCHING MAIN ACTIVIY; ENABLES US TO PASS THE FRAGMENT THAT WE WANT TO HANDLE THE EMITTED EVENT
                  * DOWNSIDE: WORK STRICTLY WITH THE ONE OBJECT PASSED TO OUR CONSTRUCTOR OR newInstance()
                  * EXECUTION CODE: callback.run(korisnik);
                  * REQUIRES: GET THE LISTENER OBJECT (IN THIS CASE FRAGMENT) ie. ON CREATING THIS FRAGMENT
                  *           - GET THE CALLBACK OBJECT, DEFINE A PROPERTY (or/and BUNDLE KEY) TO STORE THE CALLBACK
                  *           - DEFINE THE GENERIC INTERFACE
                  *           - FRAGMENT CREATES AND PASSES THE INTERFACE WITH THE OVERRIDDEN LISTENER METHOD
                 */
                callback.run(korisnik);

                getDialog().dismiss();
            }
        });

        btnNoviKorisnik = view.findViewById(R.id.btnKorisnikNovi);
        btnNoviKorisnik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnNoviKorisnikClick();
            }
        });

        btnClose = view.findViewById(R.id.btnKorisnikPretragaClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        popuniPodatke("");

        return view;
    }

    private void do_btnNoviKorisnikClick() {
        MyFragmentHelper.RunnableCallback callback = new MyFragmentHelper.RunnableCallback<KorisnikVM>() {
            @Override
            public void run(KorisnikVM korisnikVM) {
                searchViewPretraga.setQuery(korisnikVM.getIme() + " " + korisnikVM.getPrezime(), true);
                listKorisniciAdapter.notifyDataSetChanged();
            }
        };

        KorisnikAddDialogFragment frg = KorisnikAddDialogFragment.newInstance(callback);
        MyFragmentHelper.dodajDialog(getActivity(), "dodajNovogKorisnikaDialog", frg);
    }

    /*
     * FIND: "FIRST APPROACH: USING INTERFACE LISTENERS"
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyFragmentHelper.OnKorisnikPretragaClickListener) {
            KorisnikPretragaEmitter = (MyFragmentHelper.OnKorisnikPretragaClickListener) context;
        } else {
            throw new ClassCastException(context.toString() + R.string.exception_cast_interface);
        }
    }

    private void do_PretraziPrimaoce(String query) {
        popuniPodatke(query);
    }

    private void popuniPodatke(String query) {

        podaci = Storage.getKorisniciByIme(query);

        listKorisniciAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater != null ? inflater.inflate(R.layout.stavka_korisnik, viewGroup, false) : null;
                }

                TextView title = view.findViewById(R.id.stavka_korisnik_title);
                TextView subtitle = view.findViewById(R.id.stavka_korisnik_subtitle);

                title.setText(getString(R.string.stavka_korisnik_title,
                        podaci.get(i).getIme(),
                        podaci.get(i).getPrezime()));
                subtitle.setText(getString(R.string.stavka_korisnik_subtitle,
                        podaci.get(i).getOpstinaVM().getnaziv(),
                        podaci.get(i).getOpstinaVM().getdrzava()));

                return view;
            }
        };

        listKorisnici.setAdapter(listKorisniciAdapter);
    }
}
