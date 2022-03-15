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
        Log.d("Pruebita", query);
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){



    }

}
