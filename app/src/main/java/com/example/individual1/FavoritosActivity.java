package com.example.individual1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;


public class FavoritosActivity extends AppCompatActivity {

    ListView simpleList;
    private ArrayList<String> pokemonList = new ArrayList<String>();
    int pokemones[] = {R.drawable.charmander, R.drawable.bulbasaur, R.drawable.squirtle};
    private SQLiteDatabase bd;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos_main);

        // Recoger el usuario que le hemos pasado desde ListaPokemon
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        usuario = (String) b.get("user");

        MiBD dbHelper = new MiBD(this,"pokedex",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bd = db;

        int idx;
        // Ejecutar el query para conseguir la lista de favoritos asociada al usuario
        Cursor c = bd.rawQuery("SELECT * FROM Favoritos WHERE usuario=\'"+usuario+"\'" , null);
        while (c.moveToNext()) {

            idx=c.getColumnIndex("pokemon");
            pokemonList.add(c.getString(idx));

        }

        c.close();

        // Añadir el adapter a la lista para que tome el control
        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), pokemonList, pokemones);
        simpleList.setAdapter(customAdapter);


    }

}