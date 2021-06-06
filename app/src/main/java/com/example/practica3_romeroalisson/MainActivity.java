package com.example.practica3_romeroalisson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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