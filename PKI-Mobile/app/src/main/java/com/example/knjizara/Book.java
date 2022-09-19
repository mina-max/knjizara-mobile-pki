package com.example.knjizara;

import java.util.ArrayList;

public class Book {
    public int slika;
    public String naslov;
    public String autor;
    public String opis;
    public int godinaIzdanja;
    public int brojStrana;
    public Boolean promocija;
    public ArrayList<Komentar> komentari;

    public Book(int slika, String naslov, String autor, String opis, int godinaIzdanja, int brojStrana, Boolean promocija, ArrayList<Komentar> komentari) {
        this.slika = slika;
        this.naslov = naslov;
        this.autor = autor;
        this.opis = opis;
        this.godinaIzdanja = godinaIzdanja;
        this.brojStrana = brojStrana;
        this.promocija = promocija;
        this.komentari = komentari;
    }
}
