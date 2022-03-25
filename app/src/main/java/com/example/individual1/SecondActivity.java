package com.example.individual1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i("Recorrido","Paso por onCreate SecondActivity");
        PokemonInfoFragment elotro = (PokemonInfoFragment) getSupportFragmentManager().findFragmentById(R.id.Finfo);
        String n = getIntent().getStringExtra("nombre");
        String a = getIntent().getStringExtra("tipo1");
        String e = getIntent().getStringExtra("tipo2");
        Log.d("prueba", "onCreate: " + n);
        Log.d("prueba", "onCreate: " + a);
        Log.d("prueba", "onCreate: " + e);

    }
}