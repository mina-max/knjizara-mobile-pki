package com.example.knjizara;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_knjiga, parent, false);
        }
        // Lookup view for data population
        ImageView slika = (ImageView) convertView.findViewById(R.id.bookPicture);
        TextView naslov = (TextView) convertView.findViewById(R.id.bookName);
        TextView autor = (TextView) convertView.findViewById(R.id.bookAuthor);
        // Populate the data into the template view using the data object
        slika.setImageResource(book.slika);
        naslov.setText(book.naslov);
        autor.setText(book.autor);
        // Return the completed view to render on screen
        return convertView;
    }
}
