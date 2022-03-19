package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private String username;
    private String pass;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        MiBD dbHelper = new MiBD(this,"pokedex",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bd = db;

    }

//    public void setDB(SQLiteDatabase bd){
//
//        db = bd;
//
//    }

    // Comprobar los datos al darle al boton
    @SuppressLint("Range")
    public void onRegister(View v){

        // Conseguir el usuario introducido
        EditText et = findViewById(R.id.usernameR);
        username = et.getText().toString();
        // Conseguir la contraseña introducida
        EditText et2 = findViewById(R.id.passwordR);
        pass = et2.getText().toString();

        String nom = "";

        Cursor c = bd.rawQuery("SELECT usuario FROM Usuarios WHERE usuario='"+username+"'", null);

        // Comprobar si el usuario esta en la base de datos
        if (c.moveToNext()){

            // Si el usuario no esta meterlo y cambiar a main activity
            String query = "INSERT INTO Usuarios(usuario, contraseña) VALUES(\'" + username + "\', \'" + pass + "\')";
            Log.d("INsert", query);
            bd.execSQL(query);
            Intent i = new Intent(this, MainActivity.class);
            startActivityForResult(i, 66);
            //startActivity(i);

        }else {

            // Si el usuario ya esta registrado hacer un toast para avisar de que el usuario ya esta en uso
            Toast toast = Toast.makeText(this, "El usuario ya esta en uso",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();

            //Log.d("Pruebita", "UwU");

        }



    }

}