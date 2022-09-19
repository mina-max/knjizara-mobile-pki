package com.example.knjizara;

import java.util.ArrayList;

public class Korisnik {
    String ime;
    String prezime;
    String username;
    String password;
    String brojTelefona;
    String email;
    ArrayList<Book> knjige;
    ArrayList<String> preporucili;

    public Korisnik(String ime, String prezime, String username, String password, String brojTelefona, String email, ArrayList<Book> knjige, ArrayList<String> preporucili) {
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.knjige = knjige;
        this.preporucili = preporucili;
    }
}
