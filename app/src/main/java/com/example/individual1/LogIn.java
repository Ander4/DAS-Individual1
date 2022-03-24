package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    private String username;
    private String pass;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        MiBD dbHelper = new MiBD(this,"pokedex",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bd = db;
    }

    // Comprobar los datos al darle al boton entrar
    public void onEntrar(View v) {

        EditText et_u;
        EditText et_p;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // Si está en LANDSCAPE conseguir los datos de su layout

            // Conseguir el usuario introducido
            et_u = findViewById(R.id.usernameL);
            username = et_u.getText().toString();
            // Conseguir la contraseña introducida
            et_p = findViewById(R.id.passwordL);
            pass = et_p.getText().toString();
        } else {

            // Si está en PORTRAIT conseguir los datos de su layout

            // Conseguir el usuario introducido
            et_u = findViewById(R.id.username);
            username = et_u.getText().toString();
            // Conseguir la contraseña introducida
            et_p = findViewById(R.id.password);
            pass = et_p.getText().toString();

        }

        if (!username.equals("") && !pass.equals("")) {

            // Ejecutar el query para ver si el usuario y contraseña estan en la base de datos
            Cursor c = bd.rawQuery("SELECT * FROM Usuarios WHERE usuario='" + username + "' AND contraseña='" + pass + "'", null);
            // Comprobar si el usuario y contraseña son correctos
            if (c.moveToNext()) {

                // Si el usuario y contraseña son correctos cambiar al activity ListaPokemon
                Log.d("LogIn", "Has sido logeado");
                Intent i = new Intent(this, ListaPokemon.class);
                i.putExtra("user", username);
                startActivityForResult(i, 66);

                // y borrar los campos de usuario y contraseña
                et_u.setText("");
                et_p.setText("");

            } else {

                // Ejecutar un query para comprobar si el usuario está en la base de datos
                Cursor c2 = bd.rawQuery("SELECT * FROM Usuarios WHERE usuario='" + username + "'", null);

                // comprobar si el usuario está en la base de datos
                if (!c2.moveToNext()) {

                    // Si el usuario no está en la base de datos
                    Toast toast = Toast.makeText(this, "El usuario " + username + " no está registrado", Toast.LENGTH_SHORT);
                    toast.show();

                    // y borrar los campos de usuario y contraseña
                    et_u.setText("");
                    et_p.setText("");

                } else {

                    // Si el usuario está en la base de datos pero la contraseña no coincide
                    Toast toast = Toast.makeText(this, "El usuario " + username + " y la contraseña no coinciden", Toast.LENGTH_SHORT);
                    toast.show();

                    // y borrar el campo de ka contraseña
                    et_p.setText("");

                }

                c2.close();

            }

            c.close();

        } else {

            Toast toast = Toast.makeText(this, "Porfavor rellena los campos de usuario y contraseña", Toast.LENGTH_SHORT);
            toast.show();

        }

    }

}