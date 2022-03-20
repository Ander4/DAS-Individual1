package com.example.individual1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MiBD extends SQLiteOpenHelper {

    public MiBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        String query = "CREATE TABLE Usuarios(usuario VARCHAR(50) PRIMARY KEY NOT NULL, contraseña VARCHAR(50))";
        Log.d("Creada tabla Usuarios", query);
        sqLiteDatabase.execSQL(query);

        String query2 = "CREATE TABLE Pokemon(nombre VARCHAR(50) PRIMARY KEY NOT NULL, tipo1 VARCHAR(50), tipo2 VARCHAR(50))";
        Log.d("Creada tabla pokemon", query2);
        sqLiteDatabase.execSQL(query2);

        String pokemon1 = "INSERT INTO Pokemon Values('Charmander','Fuego','Ninguno')";
        Log.i("Insertar pokemon1", pokemon1);
        sqLiteDatabase.execSQL(pokemon1);

        String pokemon2 = "INSERT INTO Pokemon Values('Squirtle','Agua','Ninguno')";
        Log.i("Insertar pokemon1", pokemon2);
        sqLiteDatabase.execSQL(pokemon2);

        String pokemon3 = "INSERT INTO Pokemon Values('Bulbasaur','Planta','Veneno')";
        Log.i("Insertar pokemon1", pokemon3);
        sqLiteDatabase.execSQL(pokemon3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){



    }

}
