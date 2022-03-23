package com.example.individual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
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

    // Comprobar los datos al darle al boton register
    @SuppressLint("Range")
    public void onRegister(View v){

        // Conseguir el usuario introducido
        EditText et = findViewById(R.id.usernameR);
        username = et.getText().toString();
        // Conseguir la contraseña introducida
        EditText et2 = findViewById(R.id.passwordR);
        pass = et2.getText().toString();

        // Ejecutar el query para ver si el usuario esta en la tabla Usuarios
        Cursor c = bd.rawQuery("SELECT usuario FROM Usuarios WHERE usuario='"+username+"'", null);

        // Comprobar si el usuario esta en la base de datos
        if (!c.moveToNext()){

            // Si el usuario no esta meterlo y cambiar a main activity
            String query = "INSERT INTO Usuarios(usuario, contraseña) VALUES(\'" + username + "\', \'" + pass + "\')";
            bd.execSQL(query);

            Intent i = new Intent(this, MainActivity.class);
            startActivityForResult(i, 66);
            PendingIntent intentEnNot = PendingIntent.getActivity(this, 0, i, 0);

            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "IdCanal");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal",
                        NotificationManager.IMPORTANCE_DEFAULT);
                elManager.createNotificationChannel(elCanal);

                setContentView(R.layout.register);
                elCanal.setDescription("Descripción del canal");
                elCanal.enableLights(true);
                elCanal.setLightColor(Color.RED);
                elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                elCanal.enableVibration(true);
            }

            elBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notificacion))
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentTitle("Notificación de registro")
                    .setContentText("El usuario: "+username + " ha sido \nregistrado")
                    .setSubText("Registro nuevo")
                    .setVibrate(new long[]{0, 1000, 500, 1000})
                    .setAutoCancel(true)
                    .addAction(android.R.drawable.ic_input_add, "OK", intentEnNot);

            elManager.notify(1, elBuilder.build());
            Log.i("Notificacion","Has sido notificado");


        }else {

            // Si el usuario ya esta registrado hacer un toast para avisar de que el usuario ya esta en uso
            Toast toast = Toast.makeText(this, "El usuario ya esta en uso",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();

        }



    }

}