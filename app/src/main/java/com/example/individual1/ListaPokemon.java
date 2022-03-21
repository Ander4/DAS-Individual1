package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ListaPokemon extends AppCompatActivity implements PokemonListFragment.listenerDelFragment{

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

    @Override
    public String[] cargarElementos() {
        String [] s = new String[a.size()];
        int i = 0;
        for (Pokemon po : a)
            s[i++] = po.getNombre();

        return s;
    }

    public void seleccionarElemento(int pos) {

        String n = a.get(pos).getNombre();
        String tp1 = a.get(pos).getTipo1();
        String tp2 = a.get(pos).getTipo2();

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //EL OTRO FRAGMENT EXISTE
            PokemonInfoFragment elotro = (PokemonInfoFragment) getSupportFragmentManager().findFragmentById(R.id.Finfo);
            elotro.actualizarDatos(n,tp1,tp2);
        } else {
            //EL OTRO FRAGMENT NO EXISTE, HAY QUE LANZAR LA ACTIVIDAD QUE LO CONTIENE
            Intent i = new Intent(this, SecondActivity.class);
            i.putExtra("nombre", n);
            i.putExtra("tipo1", tp1);
            i.putExtra("tipo2", tp2);
            startActivity(i);
        }
    }

}