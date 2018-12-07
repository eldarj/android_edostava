package com.eldar.fit.seminarski.fragments;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.Korpa;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;
import com.eldar.fit.seminarski.data.api.request.models.NewNarudzbaRequest;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUrlConnection;

public class KorpaFragment extends Fragment {

    public static String Tag = "korpaFragment";
    public static String RENDER_KORPA_APPBAR = "showKorpaAppBar";

    private Korpa korpa;

    private BaseAdapter listStavkeAdapter;
    private ListView listKorpaStavke;

    private Toolbar myToolbar;
    private Button btnKorpaNaruci;
    private MaterialButton btnKorpaOdbaci;
    private ImageButton btnKorpaClose;
    private TextView textKorpaIntro, textKorpaTotal;
    private View view;
    private AppBarLayout appbarKorpa;
    private boolean renderKorpaAppBar;
    private RelativeLayout progressBarKorpaHolder;

    public static KorpaFragment newInstance(boolean renderKorpaAppBar) {
        KorpaFragment fragment = new KorpaFragment();

        Bundle args = new Bundle();
        args.putBoolean(RENDER_KORPA_APPBAR, renderKorpaAppBar);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if (getArguments().containsKey(RENDER_KORPA_APPBAR)) {
            renderKorpaAppBar = getArguments().getBoolean(RENDER_KORPA_APPBAR);
        }

        korpa = prepKorpaSession();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.korpa_fragment, container, false);

        appbarKorpa = view.findViewById(R.id.appbarKorpa);
        if (!renderKorpaAppBar) {
            appbarKorpa.getLayoutParams().height = 0;
        }

        myToolbar = view.findViewById(R.id.toolbarKorpa);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        progressBarKorpaHolder = view.findViewById(R.id.progressBarKorpaHolder);
        textKorpaIntro = view.findViewById(R.id.textKorpaIntro);
        textKorpaTotal = view.findViewById(R.id.textKorpaTotal);

        listKorpaStavke = view.findViewById(R.id.listKorpaStavke);
        listKorpaStavkePopuni();
        listKorpaStavke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KorpaHranaStavka stavka = korpa.getHranaStavke().get(position);
                MyFragmentHelper.dodajDialog((AppCompatActivity) getActivity(),
                        KorpaStavkaDialogFragment.Tag,
                        KorpaStavkaDialogFragment.newInstance(stavka));
            }
        });

        btnKorpaClose = view.findViewById(R.id.btnKorpaClose);
        btnKorpaClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnKorpaOdbaci = view.findViewById(R.id.btnKorpaOdbaci);
        btnKorpaOdbaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_odbaciKorpuClick();
            }
        });

        btnKorpaNaruci = view.findViewById(R.id.btnKorpaNaruci);
        btnKorpaNaruci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnNaruciClick();
            }
        });

        setupUI();
        return view;
    }

    private void setupUI() {
        textKorpaIntro.setText(korpa.getHranaStavke().size() == 0 ?
                getString(R.string.empty_list_korpa) :
                getString(R.string.korpa_ukupno_stavki) + korpa.getHranaStavkeTotalCount());

        textKorpaTotal.setText(korpa.getUkupnaCijena() == 0 ? "- KM" : korpa.getUkupnaCijena() + " KM");

        if (korpa.getHranaStavke().size() == 0) {
            btnKorpaNaruci.setEnabled(false);
            btnKorpaOdbaci.setEnabled(false);
            btnKorpaOdbaci.setTextColor(getResources().getColor(R.color.colorTypographySemiDark));
            btnKorpaOdbaci.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.colorTypographySemiDark)));
        }
    }

    private void do_odbaciKorpuClick() {
        progressBarKorpaHolder.setVisibility(View.VISIBLE);
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }
            @Override
            public void onFinish() {
                MySession.setKorpa(null);
                korpa = prepKorpaSession();
                setupUI();
                progressBarKorpaHolder.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    private void do_btnNaruciClick() {
        progressBarKorpaHolder.setVisibility(View.VISIBLE);

        MyApiRequest.request(MyApiRequest.ENDPOINT_NARUDZBE_CREATE,
                MyUrlConnection.HttpMethod.POST,
                new NewNarudzbaRequest(
                        new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                        MySession.getKorpa()),
                new MyAbstractRunnable<String>() {
                    @Override
                    public void run(String s) {
                        onNarudzbaCreated(s, null, null);
                    }

                    @Override
                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                        onNarudzbaCreated(null, statusCode, errorMessage);
                    }
                });

        MySession.setKorpa(null);
        korpa = prepKorpaSession();
    }

    private void onNarudzbaCreated(@Nullable String response, @Nullable Integer statusCode, @Nullable String errorMessage) {
        progressBarKorpaHolder.setVisibility(View.INVISIBLE);

        if (response != null) {
            MySession.setKorpa(null);
            korpa = prepKorpaSession();
            setupUI();
            final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder((AppCompatActivity)getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
            dlgBuilder.setTitle(R.string.korpa_dialog_narudzba_created_title)
                    .setMessage(R.string.korpa_dialog_narudzba_created_msg)
                    .setPositiveButton(R.string.korpa_dialog_narudzba_created_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MyFragmentHelper.fragmentReplace((AppCompatActivity) getActivity(),
                                    R.id.fragmentContainer,
                                    ProfilNarudzbeFragment.newInstance(response),
                                    ProfilNarudzbeFragment.Tag,
                                    true);

                        }
                    })
                    .show();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.content),
                    getString(R.string.dogodila_se_greska_posalji_narudzbu_ponovo),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private Korpa prepKorpaSession() {
        if (listStavkeAdapter != null ) {
            listStavkeAdapter.notifyDataSetChanged();
        }
        if (MySession.getKorpa() == null) {
            MySession.setKorpa(new Korpa());
        }
        return MySession.getKorpa();
    }

    private void listKorpaStavkePopuni() {
        listStavkeAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return korpa.getHranaStavke().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.korpa_stavka, parent, false) : null;
                }

                KorpaHranaStavka stavka = korpa.getHranaStavke().get(position);

                TextView textStavkaKorpaSuper = view.findViewById(R.id.textStavkaKorpaSuper);
                textStavkaKorpaSuper.setText("Restoran " + stavka.getHranaItemVM().getRestoranNaziv());

                TextView textStavkaKorpaNaziv = view.findViewById(R.id.textStavkaKorpaNaziv);
                textStavkaKorpaNaziv.setText("x" + stavka.getKolicina() + " " + stavka.getHranaItemVM().getNaziv());

                TextView textStavkaKorpaOpis = view.findViewById(R.id.textStavkaKorpaOpis);
                textStavkaKorpaOpis.setText("Cijena " + stavka.getHranaItemVM().getCijena());

                TextView textStavkaKorpaCijena = view.findViewById(R.id.textStavkaKorpaCijena);
                textStavkaKorpaCijena.setText(stavka.getUkupnaCijena() == 0 ? "- KM" : stavka.getUkupnaCijena() + " KM");

                ImageView imageStavkaKorpa = view.findViewById(R.id.imageStavkaKorpa);
                Glide.with(getActivity())
                        .load(stavka.getHranaItemVM().getImageUrl())
                        .centerCrop()
                        .into(imageStavkaKorpa);

                return view;
            }
        };

        listKorpaStavke.setAdapter(listStavkeAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_korpa_secondary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionMojeNarudzbe) {
            MyFragmentHelper.fragmentReplace((AppCompatActivity) getActivity(),
                    R.id.fragmentContainer,
                    ProfilNarudzbeFragment.newInstance(""),
                    ProfilNarudzbeFragment.Tag,
                    true);
        }

        return false;
    }
}
