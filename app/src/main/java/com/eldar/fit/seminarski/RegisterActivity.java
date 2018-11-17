package com.eldar.fit.seminarski;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

import java.util.List;

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
        List<String> blokPodaci = Storage.getStringListBlokovi();
        MyUtils.popuniSpinner(this,
                blokPodaci,
                android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerBlok);
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


        KorisnikVM noviKorisnik = new KorisnikVM(
                inputUsername.getText().toString(),
                inputPassword.getText().toString(),
                inputIme.getText().toString(),
                inputPrezime.getText().toString(),
                Storage.getBlokovi().get(spinnerBlok.getSelectedItemPosition())
        );

        MySession.setKorisnik(noviKorisnik);
        startActivity(new Intent(this, GlavniActivity.class));
        finish();

    }
}
