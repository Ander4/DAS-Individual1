package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Cambiar a la ventana de login
    public void onLogIn(View v){

        Log.i("Recorrido","Paso por onLogIn MainActivity");
        Intent i = new Intent(this, LogIn.class);
        startActivityForResult(i, 66);

    }

    // Cambiar a la ventana de registro
    public void onRegister(View v){

        Log.i("Recorrido","Paso por onRegister MainActivity");
        Intent i = new Intent(this, Register.class);
        startActivityForResult(i, 66);

    }

}