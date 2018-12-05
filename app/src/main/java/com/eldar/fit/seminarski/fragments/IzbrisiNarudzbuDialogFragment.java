package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.NarudzbaVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;

public class IzbrisiNarudzbuDialogFragment extends DialogFragment {

    public static String NARUDZBA_FOR_DELETING = "narudzbaForDeleting";
    public static String CALLBACK_FOR_DELETING = "callbackForNarudzbaDeleting";

    public static String Tag = "profilPromijeniSlikuDialogFragment";
    private NarudzbaVM narudzba;
    private TextView tvNarudzbaDeletePotvrda;
    private TextInputEditText textNarudzbaDeletePotvrda;
    private ProgressBar progressBar_izbrisiNarudzbu;
    private Button btnNarudzbaDeleteOdustani;
    private Button btnNarudzbaDeletePotvrdi;
    private View view;
    private MyFragmentHelper.RunnableCallback callback;

    public static IzbrisiNarudzbuDialogFragment newInstance(NarudzbaVM narudzba, MyFragmentHelper.RunnableCallback callback) {
        IzbrisiNarudzbuDialogFragment fragment = new IzbrisiNarudzbuDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(NARUDZBA_FOR_DELETING, narudzba);
        args.putSerializable(CALLBACK_FOR_DELETING, callback);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(NARUDZBA_FOR_DELETING)) {
            narudzba = (NarudzbaVM) getArguments().getSerializable(NARUDZBA_FOR_DELETING);
        }
        if (getArguments().containsKey(CALLBACK_FOR_DELETING)) {
            callback = (MyFragmentHelper.RunnableCallback) getArguments().getSerializable(CALLBACK_FOR_DELETING);
        }

        setStyle(STYLE_NORMAL, R.style.AlertDialogsTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.narudzba_delete_dialog, container, false);

        tvNarudzbaDeletePotvrda = view.findViewById(R.id.tvNarudzbaDeletePotvrda);
        tvNarudzbaDeletePotvrda.setText(narudzba.getuId().toString().substring(0,4));

        textNarudzbaDeletePotvrda = view.findViewById(R.id.textNarudzbaDeletePotvrda);

        progressBar_izbrisiNarudzbu = view.findViewById(R.id.progressBar_izbrisiNarudzbu);

        btnNarudzbaDeleteOdustani = view.findViewById(R.id.btnNarudzbaDeleteOdustani);
        btnNarudzbaDeleteOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        btnNarudzbaDeletePotvrdi = view.findViewById(R.id.btnNarudzbaDeletePotvrdi);
        btnNarudzbaDeletePotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textNarudzbaDeletePotvrda == null || !textNarudzbaDeletePotvrda.getText().toString().equals(narudzba.getuId().toString().substring(0, 4))) {
                    textNarudzbaDeletePotvrda.setError(getString(R.string.narudzba_neispravna_sifra));
                    Snackbar.make(getView(), R.string.narudzba_potvrdi_sifru , Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (progressBar_izbrisiNarudzbu != null) {
                    progressBar_izbrisiNarudzbu.setVisibility(View.VISIBLE);
                }

                MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_NARUDZBE_DELETE, narudzba.getId()),
                    new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                    new MyAbstractRunnable<String>() {
                        @Override
                        public void run(String response) {
                            onNarudzbaDeleted(response, null, null);
                        }

                        @Override
                        public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                            onNarudzbaDeleted(null, statusCode, errorMessage);
                        }
                    });
            }
        });
        return view;
    }

    private void onNarudzbaDeleted(@Nullable String response , @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (progressBar_izbrisiNarudzbu != null) {
            progressBar_izbrisiNarudzbu.setVisibility(View.INVISIBLE);
        }

        if (response == null) {
            Snackbar.make(getView(), R.string.dogodila_se_greska_narudzba , Snackbar.LENGTH_SHORT).show();
        } else {
            callback.run(narudzba);
            Snackbar.make(getView(), R.string.narudzba_deleted, Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    getDialog().dismiss();
                }
            }).show();
        }
    }
}
