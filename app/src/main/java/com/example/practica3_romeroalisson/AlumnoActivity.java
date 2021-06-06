package com.example.practica3_romeroalisson;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practica3_romeroalisson.entity.Alumno;
import com.example.practica3_romeroalisson.util.ConnectionRest;
import com.example.practica3_romeroalisson.api.ServiceAlumnoApi;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtDni, txtCorreo, txtFecNac;
    Button btnEnviar;
    ServiceAlumnoApi apiAlumno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        //recuperar los datos con los controles de layout
        txtNombres = findViewById(R.id.idTextNombre);
        txtApellidos = findViewById(R.id.idtTextApellidos);
        txtCorreo = findViewById(R.id.idTextCorreo);
        txtDni = findViewById(R.id.idTextDni);
        txtFecNac = findViewById(R.id.idTextFechaNac);

        btnEnviar = findViewById(R.id.idBotonAlumno);

        apiAlumno = ConnectionRest.getConnection().create(ServiceAlumnoApi.class);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombres.getText().toString();
                String ape = txtApellidos.getText().toString();
                String cor = txtCorreo.getText().toString();
                String dni = txtDni.getText().toString();
                String fecnac = txtFecNac.getText().toString();


                if (!nom.matches("[a-zA-Z\\sáéíóúÁÉÍÓÚñÑ]{2,50}")) {
                    txtNombres.setError("Su nombre ocupa de 2 a 50 caracteres");
                    txtNombres.requestFocus();
                } else if (!ape.matches("[a-zA-Z\\sáéíóúÁÉÍÓÚñÑ]{2,50}")) {
                    txtApellidos.setError("Sus apellidos ocupan de 2 a 50 caracteres");
                    txtApellidos.requestFocus();
                } else if (!dni.matches("[0-9]{8}")) {
                    txtDni.setError("Dni debe tener 8 digitos");
                    txtDni.requestFocus();
                } else if (!cor.matches("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")) {
                    txtCorreo.setError("Correo debe tener el formato alguien@algunlugar.es");
                    txtCorreo.requestFocus();
                } else if (!fecnac.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
                    txtFecNac.setError("Use formato recomendado para fecha de nacimiento");
                    txtFecNac.requestFocus();
                } else {
                    Alumno obj = new Alumno();

                    obj.setNombres(nom);
                    obj.setApellidos(ape);
                    obj.setCorreo(cor);
                    obj.setDni(dni);
                    obj.setFechaNacimiento(fecnac);


                    insertaAlumno(obj);
                    //mostrar mensaje
                    String msg = "Datos registrados satisfactoriamente\n\n";
                    msg += "Nombres : " + nom + "\n";
                    msg += "Apellidos : " + ape + "\n";
                    msg += "Dni : " + dni + "\n";
                    msg += "Correo : " + cor + "\n";
                    msg += "Fecha de Nacimiento : " + fecnac + "\n";
                    mostrarMensaje(msg);

                }
            }
        });

    }

    public void insertaAlumno(Alumno obj) {
        Call<Alumno> call = apiAlumno.insertaAlumno(obj);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()) {
                    Alumno obj = response.body();
                    if (obj == null) {
                        mensaje("ERROR " + "No se inserto ningun dato");
                    } else {
                        mensaje("EXITO " + "Si se inserto datos exitosamente " + obj.getIdAlumno());
                    }
                } else {
                    mensaje("ERROR " + " Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensaje("ERROR" + t.getMessage());
            }
        });
    }

    void mensaje(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.show();
    }

    public void mostrarMensaje(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
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
