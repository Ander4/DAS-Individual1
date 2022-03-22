package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ListaPokemon extends AppCompatActivity implements PokemonListFragment.listenerDelFragment{

    private SQLiteDatabase bd;
    private ArrayList<Pokemon> a;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pokemon);
        setSupportActionBar(findViewById(R.id.labarra));

        // Recoger el usuario que le hemos pasado desde LogIn
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        usuario = (String) b.get("user");

        MiBD dbHelper = new MiBD(this,"pokedex",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bd = db;

        a = new ArrayList<>();

        // Ejecutar el query para conseguir la lista de Pokemon de la base de datos
        Cursor c = bd.rawQuery("SELECT * FROM Pokemon" , null);

        // Añadir los pokemon de la base de datos al ArrayList
        while (c.moveToNext()){

            int nombre_c = c.getColumnIndex("nombre");
            int tipo1_c = c.getColumnIndex("tipo1");
            int tipo2_c = c.getColumnIndex("tipo2");

            a.add(new Pokemon(c.getString(nombre_c), c.getString(tipo1_c), c.getString(tipo2_c)));

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

    // Controlar el comportamiento al pulsar un elemento de la lista
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
            i.putExtra("user", usuario);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_pokemon,menu);
        return true;
    }

    // Al darle a favoritos en la barra superior cambiar a FavoritosActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            default:{

                Intent i = new Intent(this, FavoritosActivity.class);
                i.putExtra("user", usuario);
                startActivity(i);

            }
        }
        return super.onOptionsItemSelected(item);
    }

}