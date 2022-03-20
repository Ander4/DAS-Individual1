package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ListaPokemon extends AppCompatActivity {

    private SQLiteDatabase bd;
    ArrayList<Pokemon> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pokemon);

        MiBD dbHelper = new MiBD(this,"pokedex",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bd = db;

        a = new ArrayList<>();

        Cursor c = bd.rawQuery("SELECT * FROM Pokemon" , null);

        while (c.moveToNext()){

            int nombre_c = c.getColumnIndex("nombre");
            int tipo1_c = c.getColumnIndex("tipo1");
            int tipo2_c = c.getColumnIndex("tipo2");

            a.add(new Pokemon(c.getString(nombre_c), c.getString(tipo1_c), c.getString(tipo2_c)));

        }

        for (int i=0; i<a.size();i++){

            Log.i("Pokemon",a.get(i).getNombre() + " " + a.get(i).getTipo1() + " " + a.get(i).getTipo2());

        }


    }
}