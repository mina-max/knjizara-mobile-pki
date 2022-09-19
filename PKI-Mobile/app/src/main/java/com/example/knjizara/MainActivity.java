package com.example.knjizara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Korisnik> korisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        loadData();
        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Korisnik korisnik : korisnici) {
                    if (username.getText().toString().equals(korisnik.username)
                            && password.getText().toString().equals(korisnik.password)) {
                        Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();


                        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(korisnik);
                        editor.putString("korisnik", json);
                        editor.apply();

                        saveData();

                        openHomeActivity();
                    }
                }
            }
        });
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(korisnici);
        editor.putString("korisnici", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("korisnici", null);
        Type type = new TypeToken<ArrayList<Korisnik>>() {}.getType();
        korisnici = gson.fromJson(json, type);

        if (korisnici == null) {
            korisnici = new ArrayList<Korisnik>();
            korisnici.add(new Korisnik("Mirjana", "Radosavljevic", "mina"
            , "mina", "061/2439-9899", "mina@gmail.com", new ArrayList<Book>(), new ArrayList<String>()));
            korisnici.add(new Korisnik("Ivan", "Kukrkic", "ivan"
                    , "ivan", "061/2439-9899", "ivan@gmail.com",new ArrayList<Book>(), new ArrayList<String>()));
            korisnici.add(new Korisnik("Uros", "Stokovic", "stole"
                    , "stole", "061/2439-9899", "stole@gmail.com", new ArrayList<Book>(), new ArrayList<String>()));
        }
    }
}