package com.example.practica3_romeroalisson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.practica3_romeroalisson.R;
import com.example.practica3_romeroalisson.entity.Alumno;

import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {
    private final Context context;
    private final List<Alumno> lista;


    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // construccion de la vista
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_item, parent, false);
        Alumno obj = lista.get(position);

        TextView txtNombres = (TextView) row.findViewById(R.id.idItemNombreAlumno);
        TextView txtApellidos = (TextView) row.findViewById(R.id.idItemApellidoAlumno);

        txtNombres.setText(obj.getNombres());
        txtApellidos.setText(obj.getApellidos());
        return row;
    }
}

