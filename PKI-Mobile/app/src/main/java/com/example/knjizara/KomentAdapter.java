package com.example.knjizara;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class KomentAdapter extends ArrayAdapter<Komentar> {

    public KomentAdapter(@NonNull Context context, ArrayList<Komentar> komentars) {
        super(context, 0, komentars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Komentar komentar = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView ocena = (TextView) convertView.findViewById(R.id.ocena);
        TextView kom = (TextView) convertView.findViewById(R.id.komentar);
        TextView ime = (TextView) convertView.findViewById(R.id.ime);
        // Populate the data into the template view using the data object
       ocena.setText(komentar.rating);
        kom.setText(komentar.comment);
        ime.setText(komentar.name);
        // Return the completed view to render on screen
        return convertView;
    }

}
