package com.example.knjizara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    Korisnik korisnik;
    ArrayList<Korisnik> korisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        loadData();

        ImageButton homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });

        ImageButton exitBtn = (ImageButton) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        ImageButton starBtn = (ImageButton) findViewById(R.id.favBtn);
        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStarActivity();
            }
        });

        ImageButton accountBtn = (ImageButton) findViewById(R.id.accountBtn);
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountActivity();
            }
        });

        Button promeniLozinkuBtn = (Button) findViewById(R.id.promeniLozinku);
        promeniLozinkuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChangePasswordDialog();
            }
        });
        EditText ime = (EditText) findViewById(R.id.ime);
        ime.setText(korisnik.ime);

        EditText prezime = (EditText) findViewById(R.id.prezime);
        prezime.setText(korisnik.prezime);
        EditText email  = (EditText) findViewById(R.id.email);

        email.setText(korisnik.email);
        EditText telefon = (EditText) findViewById(R.id.telefon);
        telefon.setText(korisnik.brojTelefona);

        Button sacuvajIzmeneBtn = (Button) findViewById(R.id.sacuvaj);
        sacuvajIzmeneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                korisnik.ime = ime.getText().toString();
                korisnik.prezime = prezime.getText().toString();
                korisnik.email = email.getText().toString();
                korisnik.brojTelefona = telefon.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(korisnik);
                editor.putString("korisnik", json);
                editor.apply();
                Toast.makeText(AccountActivity.this, "Uspesno ste sacuvali izmene", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < korisnici.size(); i++){
                    if(korisnici.get(i).username.equals(korisnik.username)){
                        korisnici.set(i, korisnik);
                    }
                }
                saveData();

            }
        });

        Button odbaciIzmeneBtn = (Button) findViewById(R.id.odbaci);
        odbaciIzmeneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ime.setText(korisnik.ime);
                prezime.setText(korisnik.prezime);
                email.setText(korisnik.email);
                telefon.setText(korisnik.brojTelefona);
            }
        });


    }
    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openStarActivity(){
        Intent intent = new Intent(this, StarActivity.class);
        startActivity(intent);
    }
    public void openAccountActivity(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void createChangePasswordDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View lozinkaView = getLayoutInflater().inflate(R.layout.promeni_lozinku, null);

        dialogBuilder.setPositiveButton("Promeni", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialogObj = Dialog.class.cast(dialog);
                EditText pass = (EditText) dialogObj.findViewById(R.id.lozinka1);
                korisnik.password = pass.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(korisnik);
                editor.putString("korisnik", json);


                editor.apply();

                for(int i = 0; i < korisnici.size(); i++){
                    if(korisnici.get(i).username.equals(korisnik.username)){
                        korisnici.set(i, korisnik);
                    }
                }
                saveData();


                Toast.makeText(AccountActivity.this, "Uspesno ste promenili lozinku!", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton("Odustani", null);

        dialogBuilder.setView(lozinkaView);
        dialog = dialogBuilder.create();
        dialog.show();

    }


    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(korisnici);
        editor.putString("korisnici", json);
        editor.apply();
    }


    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonKorisnik = sharedPreferences.getString("korisnik", null);
        Type typeKorisnik = new TypeToken<Korisnik>() {}.getType();
        korisnik = gson.fromJson(jsonKorisnik, typeKorisnik);

        String json = sharedPreferences.getString("korisnici", null);
        Type type = new TypeToken<ArrayList<Korisnik>>() {}.getType();
        korisnici = gson.fromJson(json, type);
    }
}