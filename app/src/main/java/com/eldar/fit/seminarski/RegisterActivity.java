package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.eldar.fit.seminarski.data.ApiBlokList;
import com.eldar.fit.seminarski.data.AuthRegister;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.ApiResponseHandler;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

import java.util.List;

import static com.eldar.fit.seminarski.helper.MyApiRequest.ENDPOINT_USER_REGISTER_AUTH;
import static com.eldar.fit.seminarski.helper.MyApiRequest.ENDPOINT_LOCATIONS;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText inputUsername, inputPassword, inputIme, inputPrezime;
    private Spinner spinnerBlok;
    private Button btnRegister, btnOpenLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputRegisterUsername);
        inputPassword = findViewById(R.id.inputRegisterPassword);
        inputIme = findViewById(R.id.inputRegisterIme);
        inputPrezime = findViewById(R.id.inputRegisterPrezime);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnRegisterClick();
            }
        });

        btnOpenLogin = findViewById(R.id.btnOpenLogin);
        btnOpenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnOpenLoginClick();
            }
        });

        spinnerBlok = findViewById(R.id.spinnerRegisterBlok);
        MyApiRequest.get(this, ENDPOINT_LOCATIONS, new MyAbstractRunnable<ApiBlokList>() {
            @Override
            public void run(ApiBlokList apiBlokList) {
                List<String> blokPodaci = ApiResponseHandler.getStringListBlokovi(apiBlokList);
                onBlokPodaciReceived(blokPodaci, null, null);

            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                onBlokPodaciReceived(null, statusCode, errorMessage);
            }
        });

    }

    private void onBlokPodaciReceived(@Nullable List<String> blokPodaci, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (findViewById(R.id.progressBar_blokSpinner) != null) {
            findViewById(R.id.progressBar_blokSpinner).setVisibility(View.INVISIBLE);
        }

        if (blokPodaci != null) {
            MyUtils.popuniSpinner(this,
                    blokPodaci,
                    android.R.layout.simple_spinner_item,
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerBlok);
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    errorMessage != null ? errorMessage : "Dogodila se greška.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void do_btnOpenLoginClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void do_btnRegisterClick() {
        if (inputPassword == null || inputPassword.length() <= 4) {
            inputPassword.setError(getString(R.string.register_error_password));
        } else {
            inputPassword.setError(null); // Clear the error
        }
        if (inputUsername == null || inputUsername.length() <= 4) {
            inputUsername.setError(getString(R.string.register_error_username));
        } else if(Storage.UsernameExists(inputUsername.getText().toString())) {
            inputUsername.setError(getString(R.string.register_error_username_taken));
        } else {
            inputUsername.setError(null); // Clear the error
        }
        if (inputIme == null || inputIme.length() <= 4) {
            inputIme.setError(getString(R.string.register_error_ime));
        } else {
            inputIme.setError(null); // Clear the error
        }
        if (inputPrezime == null || inputPrezime.length() <= 4) {
            inputPrezime.setError(getString(R.string.register_error_prezime));
        } else {
            inputPrezime.setError(null); // Clear the error
        }

        if (inputUsername.getError() != null
                || inputPassword.getError() != null
                || inputIme.getError() != null
                || inputPassword.getError() != null) {
            Snackbar.make(findViewById(android.R.id.content), "Molimo provjerite podatke!", Snackbar.LENGTH_LONG).show();
            return;
        }

        AuthRegister newAccount = new AuthRegister(
                0,
                inputUsername.getText().toString(),
                inputPassword.getText().toString(),
                inputIme.getText().toString(),
                inputPrezime.getText().toString(),
                "",
                "",
                ApiResponseHandler.getBlokovi().get(spinnerBlok.getSelectedItemPosition()).getId()
        );

        MyApiRequest.post(this, ENDPOINT_USER_REGISTER_AUTH, newAccount, new MyAbstractRunnable<KorisnikVM>() {
            @Override
            public void run(KorisnikVM korisnikVM) {
                Log.i("Test", "run, result: " + korisnikVM.getUsername());
                loginUser(korisnikVM, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                loginUser(null, statusCode, errorMessage);
            }
        });
    }
    private void loginUser(@Nullable KorisnikVM korisnik, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (findViewById(R.id.progressBar_login) != null) {
            findViewById(R.id.progressBar_login).setVisibility(View.INVISIBLE);
        }
        if (korisnik == null) {
            Snackbar.make(findViewById(android.R.id.content), errorMessage != null ? errorMessage : "Pogrešan username ili password." , Snackbar.LENGTH_LONG).show();
        } else {
            MySession.setKorisnik(korisnik);
            Snackbar.make(findViewById(android.R.id.content), "Uspješno ste se registrovali!" , Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(this, GlavniActivity.class));
            finish();
        }
    }
}
