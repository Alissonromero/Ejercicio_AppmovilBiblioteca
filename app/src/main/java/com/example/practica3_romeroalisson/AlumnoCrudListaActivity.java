package com.example.practica3_romeroalisson;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practica3_romeroalisson.adapter.AlumnoAdapter;
import com.example.practica3_romeroalisson.api.ServiceAlumnoApi;
import com.example.practica3_romeroalisson.entity.Alumno;
import com.example.practica3_romeroalisson.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoCrudListaActivity extends AppCompatActivity {
    List<Alumno> lstData = new ArrayList<Alumno>();
    AlumnoAdapter adaptador = null;
    ListView lstView = null;
    ServiceAlumnoApi api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_lista);

        lstView= (ListView) findViewById(R.id.idListCrudAlumno);
        adaptador= new AlumnoAdapter(this, R.layout.activity_alumno_item,lstData);
        lstView.setAdapter(adaptador);

        api= ConnectionRest.getConnection().create(ServiceAlumnoApi.class);
        lista();
    }

    void mensaje(String msg){
        Toast toast1 =  Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void lista(){

        Call<List<Alumno>> call= api.listaAlumno();

        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                  if (response.isSuccessful()){
                      List<Alumno> data = response.body();
                      mensaje("LOG " + " size" + data.size());
                      lstData.clear();
                      lstData.addAll(data);
                      adaptador.notifyDataSetChanged();
                  }else{
                      mensaje("ERROR " + "Error en la respuesta");
                  }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                mensaje("ERROR " + "Error maximo de la app");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        int id = menu.getItemId();

        if (id == R.id.idAlumno){
            Intent intent = new Intent(this,AlumnoActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudAlumno){
            Intent intent = new Intent(this,AlumnoCrudListaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(menu);
    }

}