package com.michell.demo_crud_sqlite;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MICHELL on 27/06/2017.
 */

public class LVMainAdapter extends ArrayAdapter<Persona> {
    public LVMainAdapter(Context context) {
        super(context, 0, new ArrayList<Persona>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_item, parent, false);

        TextView tvFullName, tvEdad, tvDocumento, tvTelefono, tvDisponible;

        tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);
        tvEdad = (TextView) convertView.findViewById(R.id.tvEdad);
        tvDocumento = (TextView) convertView.findViewById(R.id.tvDocumento);
        tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);
        tvDisponible = (TextView) convertView.findViewById(R.id.tvDisponible);

        Persona persona = getItem(position);

        tvFullName.setText(persona.getNombre() + " " + persona.getApellido());
        tvEdad.setText(String.valueOf(persona.getEdad()));
        tvDocumento.setText(persona.getDocumento());
        tvTelefono.setText(persona.getTelefono());
        tvDisponible.setText(String.valueOf(persona.isDisponible()));

        return convertView;
    }
}
