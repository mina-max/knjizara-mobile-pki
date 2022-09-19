package com.example.knjizara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StarActivity extends AppCompatActivity {
    ArrayList<Book> knjige;
    ArrayList<Book> preporuceneKnjige;

    GridView gv;
    Korisnik korisnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

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

        BookAdapter adapter = new BookAdapter(this,preporuceneKnjige);

        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(adapter);

//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String bookName = ((TextView) view.findViewById(R.id.bookName)).getText().toString();
//                Book knjiga = null;
//                for(int j = 0; j < knjige.size(); j++){
//                    if(knjige.get(j).naslov.equals(bookName)){
//                        knjiga = knjige.get(j);
//                    }
//                }
//                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(knjiga);
//                editor.putString("knjiga", json);
//                editor.apply();
//
//                openBookActivity();
//            }
//        });


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

    public void openBookActivity(){
        Intent intent = new Intent(this, BookActivity.class);
        startActivity(intent);
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("korisnik", null);
        Type type = new TypeToken<Korisnik>() {}.getType();
        korisnik = gson.fromJson(json, type);

        knjige = korisnik.knjige;
        preporuceneKnjige = korisnik.knjige;

        for(int i = 0; i < preporuceneKnjige.size(); i++){
            preporuceneKnjige.get(i).autor = preporuceneKnjige.get(i).autor + "\nPreporucili: " + korisnik.preporucili.get(i);
        }
    }




}