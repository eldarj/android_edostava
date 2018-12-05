package com.eldar.fit.seminarski.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.ApiUserImage;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProfilPromijeniSlikuDialogFragment extends DialogFragment {

    public static String Tag = "profilPromijeniSlikuDialogFragment";

    private static final int REQUEST_IMAGE_CAMERA_CAPTURE = 1;
    private static final int REQUEST_IMAGE_FROM_GALLERY = 2;
    private View view;
    private ImageView imageProfil;
    private DialogInterface izborDialog;
    private Button btnProfilSlikaSnimi;
    private Button btnProfilSlikaOdustani;
    private KorisnikVM korisnik;

    public static ProfilPromijeniSlikuDialogFragment newInstance() {
        ProfilPromijeniSlikuDialogFragment fragment = new ProfilPromijeniSlikuDialogFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AlertDialogsTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profil_promijeni_sliku_dialog, container, false);

        korisnik = MySession.getKorisnik();

        btnProfilSlikaSnimi = view.findViewById(R.id.btnProfilSlikaSnimi);
        btnProfilSlikaSnimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
                    view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.VISIBLE);
                }

                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                Bitmap imageBitmap = ( (BitmapDrawable) imageProfil.getDrawable()).getBitmap();

                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream);
                byte[] imageBytes = byteStream.toByteArray();
                String encodeImageBytes = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                MyApiRequest.post(MyApiRequest.ENDPOINT_USER_UPLOAD_IMAGE,
                        new ApiUserImage(encodeImageBytes, korisnik.getUsername(), new AuthLogin(korisnik.getUsername(), korisnik.getPassword())),
                        new MyAbstractRunnable<String>() {
                    @Override
                    public void run(String path) {
                        onImageUploaded(path, null, null);
                    }

                    @Override
                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                        onImageUploaded(null, statusCode, errorMessage);
                    }
                });
            }
        });
        btnProfilSlikaOdustani = view.findViewById(R.id.btnProfilSlikaOdustani);
        btnProfilSlikaOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        imageProfil = view.findViewById(R.id.imageProfilPromijeniSlikuTrenutna);
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder dlgBuilder = new AlertDialog.Builder((AppCompatActivity)getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                dlgBuilder.setMessage(R.string.profil_slika_izbor)
                        .setPositiveButton(R.string.profil_slika_galerija, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imageProfilFromGallery();
                                izborDialog = dialog;
                            }
                        })
                        .setNegativeButton(R.string.profil_slika_kamera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imageProfilCameraCapture();
                                izborDialog = dialog;
                            }
                        })
                        .show();
            }
        });

        Glide.with(getActivity())
                .load(korisnik.getImageUrlForDisplay())
                .centerCrop()
                .into(imageProfil);

        return view;
    }

    private void onImageUploaded(@Nullable String imagePath, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
            view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.INVISIBLE);
        }

        if (imagePath != null) {
            korisnik.setImageUrl(imagePath);
            MySession.setKorisnik(korisnik);

            Snackbar.make(view, R.string.profil_slika_changed , Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    getDialog().dismiss();
                }
            }).show();

        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content),
                    errorMessage != null ? errorMessage : getString(R.string.dogodila_se_greska_profil_slika),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void imageProfilCameraCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAMERA_CAPTURE);
    }

    private void imageProfilFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extrasReturned = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extrasReturned.get("data");

            // Set the bitmap to imageProfil
            imageProfil.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(pickedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImage);
                imageProfil.setImageBitmap(bitmap);

                is.close();
            } catch (Exception e) {
                Snackbar.make(view, getString(R.string.dogodila_se_greska_profil_slika), Snackbar.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        izborDialog.dismiss();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
