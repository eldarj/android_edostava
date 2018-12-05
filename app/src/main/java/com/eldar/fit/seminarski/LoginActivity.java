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
import android.widget.ProgressBar;

import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

public class LoginActivity extends AppCompatActivity {

    private static String BUNDLE_KEY_USERNAME = "MyBundleKeyUsername";
    private static String BUNDLE_KEY_PASSWORD = "MyBundleKeyPassword";

    private TextInputEditText inputUsername, inputPassword;
    private Button btnLogin, btnOpenRegistration;
    private ProgressBar progressBar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar_login = findViewById(R.id.progressBar_login);

        inputUsername = findViewById(R.id.inputLoginUsername);
        inputPassword = findViewById(R.id.inputLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnLoginClick();
            }
        });

        btnOpenRegistration = findViewById(R.id.btnOpenRegistration);
        btnOpenRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnOpenRegistrationClick();
            }
        });

        try {
            if (savedInstanceState.containsKey(BUNDLE_KEY_USERNAME)) {
                inputUsername.setText(BUNDLE_KEY_USERNAME);
            }
            if (savedInstanceState.containsKey(BUNDLE_KEY_USERNAME)) {
                inputPassword.setText(BUNDLE_KEY_PASSWORD);
            }
        } catch (NullPointerException e) {
            Log.i("Error", "login nullpointer on savedinstance");
            // e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putString(BUNDLE_KEY_USERNAME, inputUsername.getText().toString());
            outState.putString(BUNDLE_KEY_PASSWORD, inputPassword.getText().toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void do_btnOpenRegistrationClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void do_btnLoginClick() {
        MyUtils.dismissKeyboard(this);
        progressBar_login.setVisibility(View.VISIBLE);

        if (inputPassword.length() < 3) {
            inputPassword.setError(getString(R.string.login_error_password));
        } else {
            inputPassword.setError(null); // Clear the error
        }
        if (inputUsername.length() < 3) {
            inputUsername.setError(getString(R.string.login_error_username));
        } else {
            inputUsername.setError(null); // Clear the error
        }

        if (inputUsername.getError() != null || inputPassword.getError() != null) {
            Snackbar.make(findViewById(android.R.id.content), R.string.input_podaci_invalid, Snackbar.LENGTH_LONG).show();
            findViewById(R.id.progressBar_login).setVisibility(View.INVISIBLE);
            return;
        }

        AuthLogin credentialsObj = new AuthLogin(inputUsername.getText().toString(), inputPassword.getText().toString());
        MyApiRequest.post(MyApiRequest.ENDPOINT_USER_LOGIN_CHECK_AUTH, credentialsObj, new MyAbstractRunnable<KorisnikVM>() {
            @Override
            public void run(KorisnikVM korisnikVM) {
                loginUser(korisnikVM, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                loginUser(null, statusCode, errorMessage);
            }

        });
    }

    private void loginUser(@Nullable KorisnikVM korisnik, @Nullable Integer statusCode, @Nullable String errorMessage) {
        progressBar_login.setVisibility(View.INVISIBLE);
        if (korisnik == null) {
            Snackbar.make(findViewById(android.R.id.content),
                    errorMessage != null ? errorMessage : getString(R.string.wrong_credentials) ,
                    Snackbar.LENGTH_LONG).show();
        } else {
            MySession.setKorisnik(korisnik);
            startActivity(new Intent(this, GlavniActivity.class));
            finish();
        }
    }
}
