package com.example.knjizara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    public EditText username;
    public KomentAdapter adapter;
    private ListView lv;
    public ArrayList<Komentar> arrayOfComments;
    public Korisnik korisnik;
    public Book knjiga;
    public boolean komentarisao = false;
    public ArrayList<Book> knjige;
    public ArrayList<Korisnik> korisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
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

        Button preporuciButton = (Button) findViewById(R.id.preporuciBtn);
        preporuciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRecommendBookDialog();
            }
        });

        adapter = new KomentAdapter(this,arrayOfComments);


        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);


        Button komBtn = (Button) findViewById(R.id.komentarBtn);
        komBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText ocena = (EditText)findViewById(R.id.ocenaKorisnika);
                EditText komentar = (EditText)findViewById(R.id.komentarKorisnika);
                arrayOfComments.add(new Komentar("Ocena: " + ocena.getText().toString(), komentar.getText().toString(), korisnik.username));
                adapter.notifyDataSetChanged();
                ocena.setText("");
                komentar.setText("");

                TextView o = (TextView) findViewById(R.id.ocenaText);
                TextView k = (TextView) findViewById(R.id.komText);
                o.setVisibility(View.GONE);
                k.setVisibility(View.GONE);

                komentarisao = true;
                komBtn.setVisibility(View.GONE);
                ocena.setVisibility(View.GONE);
                komentar.setVisibility(View.GONE);



                knjiga.komentari = arrayOfComments;

                for(int i = 0; i < knjige.size(); i++){
                    if(knjige.get(i).naslov.equals(knjiga.naslov)){
                        knjige.get(i).komentari = arrayOfComments;
                    }
                }

                for(int i = 0; i < korisnici.size(); i++){
                    Korisnik kor = korisnici.get(i);
                    for(int j = 0; j < kor.knjige.size(); j++){
                        if(kor.knjige.get(j).naslov.equals(knjiga.naslov)){
                            kor.knjige.set(j, knjiga);
                        }
                    }
                }

                for(int i = 0; i < korisnik.knjige.size(); i++){
                    if(korisnik.knjige.get(i).naslov.equals(knjiga.naslov)){
                        korisnik.knjige.set(i, knjiga);
                    }
                }



                saveData();
            }
        });

        ImageView img = (ImageView) findViewById(R.id.slika);
        img.setImageResource(knjiga.slika);

        TextView naslov = (TextView) findViewById(R.id.naslov);
        naslov.setText(knjiga.naslov);

        TextView autor = (TextView) findViewById(R.id.autor);
        autor.setText(knjiga.autor);

        if(knjiga.promocija){
            TextView promocija = (TextView) findViewById(R.id.promocija);
            promocija.setVisibility(View.VISIBLE);
        } else {
            TextView promocija = (TextView) findViewById(R.id.promocija);
            promocija.setVisibility(View.INVISIBLE);
        }

        TextView opis = (TextView) findViewById(R.id.opis);
        opis.setText(knjiga.opis);

        for(int i = 0; i < arrayOfComments.size(); i++){
            if(arrayOfComments.get(i).name.equals(korisnik.username)){
                EditText ocena = (EditText)findViewById(R.id.ocenaKorisnika);
                EditText komentar = (EditText)findViewById(R.id.komentarKorisnika);

                TextView o = (TextView) findViewById(R.id.ocenaText);
                TextView k = (TextView) findViewById(R.id.komText);
                o.setVisibility(View.GONE);
                k.setVisibility(View.GONE);

                komBtn.setVisibility(View.GONE);
                ocena.setVisibility(View.GONE);
                komentar.setVisibility(View.GONE);
            }
        }

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


    public void createRecommendBookDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View recommendBookView = getLayoutInflater().inflate(R.layout.bookpopup, null);

        dialogBuilder.setPositiveButton("Preporuči", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialogObj = Dialog.class.cast(dialog);
                username = (EditText) dialogObj.findViewById(R.id.korisnickoime);
                boolean exists = false;
                for (int i = 0; i < korisnici.size(); i++) {
                    Korisnik k = korisnici.get(i);
                    if (k.username.equals(username.getText().toString())) {
                        int m = 0;
                        for(int j = 0; j < k.knjige.size(); j++){
                            if(k.knjige.get(j).naslov.equals(knjiga.naslov)){
                                exists = true;
                                m = j;
                            }
                        }
                        if (exists) {
                            if (!k.preporucili.get(m).equals(korisnik.username)) {
                                k.preporucili.set(m, k.preporucili.get(m) + ", " + korisnik.username);
                            }
                        }
                        else{
                            k.knjige.add(knjiga);
                            k.preporucili.add(korisnik.username);
                        }
                        //korisnik = k;
                    }
                }

                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(korisnici);
                editor.putString("korisnici", json);
                editor.apply();

//                String jsonK = gson.toJson(korisnik);
//                editor.putString("korisnik", json);
//                editor.apply();

                Toast.makeText(BookActivity.this, "Uspesno ste preporucili knjigu!", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Odustani", null);

        dialogBuilder.setView(recommendBookView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(knjige);
        editor.putString("knjige", json);
        editor.apply();

        String jsonK = gson.toJson(knjiga);
        editor.putString("knjiga", jsonK);
        editor.apply();

        String jsonKor = gson.toJson(korisnici);
        editor.putString("korisnici", jsonKor);
        editor.apply();


        String jsonKorisnik = gson.toJson(korisnik);
        editor.putString("korisnik", jsonKorisnik);
        editor.apply();


    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("knjige", null);
        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
        knjige = gson.fromJson(json, type);

        String jsonKorisnik = sharedPreferences.getString("korisnik", null);
        Type typeKorisnik = new TypeToken<Korisnik>() {}.getType();
        korisnik = gson.fromJson(jsonKorisnik, typeKorisnik);

        String jsonKnjiga = sharedPreferences.getString("knjiga", null);
        Type typeKnjiga = new TypeToken<Book>() {}.getType();
        knjiga = gson.fromJson(jsonKnjiga, typeKnjiga);

        String jsonKorisnici = sharedPreferences.getString("korisnici", null);
        Type TypeKorisnici = new TypeToken<ArrayList<Korisnik>>() {}.getType();
        korisnici = gson.fromJson(jsonKorisnici, TypeKorisnici);

        arrayOfComments = knjiga.komentari;

    }
}