package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    private String username;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    // Comprobar los datos al darle al boton
    public void onEntrar(View v){

        // Conseguir el usuario introducido
        EditText et = findViewById(R.id.username);
        username = et.getText().toString();
        // Conseguir la contraseña introducida
        EditText et2 = findViewById(R.id.password);
        pass = et2.getText().toString();

        // Si el usuario y la contraseña son correctos cambiar a la ventana correspondiente
        if (username.equals("Ander") && pass.equals("1234")){

            Intent i = new Intent(this, MainActivity.class);
            startActivityForResult(i, 66);
            //startActivity(i);

        }else {

            // Si el usuario y la contraseña no coinciden crear un toast para avisar de que ha habido un error en el login
            // y borrar los campos de usuario y contraseña
            Toast toast = Toast.makeText(this, "Login error",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();

            et.setText("");
            et2.setText("");

        }



    }

}