package com.example.knjizara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private GridView gv;
    private ArrayList<Book> knjige;
    private Korisnik korisnik;
    private ArrayList<Korisnik> korisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadData();

        ImageButton homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
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

        ImageButton exitBtn = (ImageButton) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        BookAdapter adapter = new BookAdapter(this,knjige);

        gv = (GridView) findViewById(R.id.gridViewSve);
        gv.setAdapter(adapter);

        ArrayList<Book> arrayOfBooksPromocija = new ArrayList<Book>();
        BookAdapter adapterP = new BookAdapter(this,arrayOfBooksPromocija);

        for(int i = 0; i < knjige.size(); i++){
            if(knjige.get(i).promocija){
                adapterP.add(knjige.get(i));
            }
        }
        GridView gvPromocija = (GridView) findViewById(R.id.gridViewPromocija);
        gvPromocija.setAdapter(adapterP);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String bookName = ((TextView) view.findViewById(R.id.bookName)).getText().toString();
                Book knjiga = null;
                for(int j = 0; j < knjige.size(); j++){
                    if(knjige.get(j).naslov.equals(bookName)){
                        knjiga = knjige.get(j);
                    }
                }
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(knjiga);
                editor.putString("knjiga", json);
                editor.apply();
                openBookActivity();
            }
        });

        gvPromocija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String bookName = ((TextView) view.findViewById(R.id.bookName)).getText().toString();
                Book knjiga = null;
                for(int j = 0; j < knjige.size(); j++){
                    if(knjige.get(j).naslov.equals(bookName)){
                        knjiga = knjige.get(j);
                    }
                }
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(knjiga);
                editor.putString("knjiga", json);
                editor.apply();

                openBookActivity();
            }
        });




        saveData();


    }
    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openBookActivity(){
        Intent intent = new Intent(this, BookActivity.class);
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

    private void saveData(){
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

        String jsonK = gson.toJson(korisnici);
        editor.putString("korisnici", jsonK);
        editor.apply();

        String jsonKnjige = gson.toJson(knjige);
        editor.putString("knjige", jsonKnjige);
        editor.apply();
    }


    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("knjige", null);
        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
        knjige = gson.fromJson(json, type);

        if (knjige == null) {
            knjige = new ArrayList<Book>();
            knjige.add(new Book(R.drawable.b7809f1a_5c3a_49de_ac46_56369cb23065_godshot,
                    "Godshot", "Chelsea Bieker", "Suša je obuzela grad Peaches u Kaliforniji. Oblast Centralne doline u kojoj žive četrnaestogodišnja Lejsi Mej i njena majka alkoholičarka nekada je bila poljoprivredni raj. Sada je to ekološka katastrofa, mesto napuknute zemlje i neplodnih farmi grožđa. U svom očaju, stanovnici su se za savet obratili vođi kulta po imenu Pastor Vern. On obećava, kroz tajne „zadatke“, da će doneti kišu za koju se svi mole.", 2020,
                    336, true, new ArrayList<Komentar>()
            ));

            knjige.get(0).komentari.add(new Komentar("Ocena: 5", " Kakva fantastična, očaravajuća i krajnje srceparajuća priča. " +
                    "Počeo sam da čitam ovu knjigu pre spavanja (greška), a onda sam je odmah podigao kada " +
                    "sam se probudio i nisam je ponovo spustio dok nisam završio.", "stole"));
            knjige.get(0).komentari.add(new Komentar("Ocena: 5", " Duboko udahnite pre nego što počnete da čitate ovaj" +
                    " mračni, blistavi i divno očaravajući roman, jer neće proći mnogo vremena pre nego što ćete " +
                    "zadržati dah u iščekivanju onoga što vas čeka u Kaliforniji.\n", "ivan"));

            knjige.add(new Book(R.drawable._781616207861,
                    "Why We Swim", "Bonnie Tsui", "Plivamo u ledenim vodama Arktika i rekama prepunim pirana da bismo testirali svoje granice. Plivamo iz zadovoljstva, za vežbanje, za lečenje. Ali ljudi, za razliku od drugih životinja koje privlači voda, nisu prirodno rođeni plivači. Moramo biti naučeni. Naši evolucioni preci su učili za preživljavanje; sada, u dvadeset prvom veku, plivanje je jedna od najpopularnijih aktivnosti na svetu.", 2020,
                    336, true, new ArrayList<Komentar>()
            ));

            knjige.add(new Book(R.drawable._1bqz44weul,
                    "Luminous Rep", "Andres Barba", "San Kristobal je bio neupadljiv grad - mali, tek prosperitetni, okružen kišnom šumom i rekom. Ali onda su stigla deca. Niko nije znao odakle su došli: trideset dvoje dece, naizgled rođenih iz džungle, govore nepoznatim jezikom. U početku su sakupljali, krali hranu i novac i bežali na drveće. Ali njihovi prestupi su eskalirali u nasilje, a onda su deca iz grada počela da beže da im se pridruže. Suočene sa potpunim kolapsom, opštinske snage kreću u lov da pronađu decu pre nego što grad zapadne u nepopravljivi haos.", 2020,
                    336, false, new ArrayList<Komentar>()
            ));

            knjige.add(new Book(R.drawable.sin_eater_by_megan_campisi,
                    "Sin Eater", "Megan Campisi", "San Kristobal je bio neupadljiv grad - mali, tek prosperitetni, okružen kišnom šumom i rekom. Ali onda su stigla deca. Niko nije znao odakle su došli: trideset dvoje dece, naizgled rođenih iz džungle, govore nepoznatim jezikom. U početku su sakupljali, krali hranu i novac i bežali na drveće. Ali njihovi prestupi su eskalirali u nasilje, a onda su deca iz grada počela da beže da im se pridruže. Suočene sa potpunim kolapsom, opštinske snage kreću u lov da pronađu decu pre nego što grad zapadne u nepopravljivi haos.", 2020,
                    336, false, new ArrayList<Komentar>()
            ));
        }

        String jsonKorisnik = sharedPreferences.getString("korisnik", null);
        Type typeKorisnik = new TypeToken<Korisnik>() {}.getType();
        korisnik = gson.fromJson(jsonKorisnik, typeKorisnik);

        if(korisnik.username.equals("mina") && korisnik.knjige.isEmpty()) {
            korisnik.knjige.add(knjige.get(0));
            korisnik.preporucili.add("ivan");
        }

        if(korisnik.username.equals("ivan") && korisnik.knjige.isEmpty()) {
            korisnik.knjige.add(knjige.get(1));
            korisnik.preporucili.add("mina");
        }
        String jsonK = sharedPreferences.getString("korisnici", null);
        Type typeK = new TypeToken<ArrayList<Korisnik>>() {}.getType();
        korisnici = gson.fromJson(jsonK, typeK);


    }


}