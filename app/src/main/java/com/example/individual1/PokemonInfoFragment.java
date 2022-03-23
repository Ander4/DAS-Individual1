package com.example.individual1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class PokemonInfoFragment extends Fragment {

    private String nombre, tipo1, tipo2, link, usuario;
    private TextView tv_nombre, tv_tipo1, tv_tipo2;
    private ImageView iv_img;
    private Button btn_info, btn_fav;
    private SQLiteDatabase bd;
    private Context contexto;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.contexto = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.pokemon_info_fragment,container,false);
        Log.d("prueba", "onCreateView: paso por aquí");
        return v;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // Recoger el usuario que le hemos pasado desde ListaPokemon
        Intent iin = this.getActivity().getIntent();
        Bundle b = iin.getExtras();
        nombre = (String) b.get("nombre");
        tipo1 = (String) b.get("tipo1");
        tipo2 = (String) b.get("tipo2");
        usuario = (String) b.get("user");
        link = "https://www.wikidex.net/wiki/"+nombre;

        iniciarBD();

        // Cambiar los elementos del layout a corde c los parametros recividos
        tv_nombre= getView().findViewById(R.id.nombre);
        tv_tipo1= getView().findViewById(R.id.tipo1);
        tv_tipo2= getView().findViewById(R.id.tipo2);
        btn_info=getView().findViewById(R.id.info);
        btn_fav=getView().findViewById(R.id.favs);
        iv_img=getView().findViewById(R.id.imageView);
        tv_nombre.setText(nombre);
        tv_tipo1.setText(tipo1);
        tv_tipo2.setText(tipo2);
        setImage();

        Log.d("prueba", "onActivityCreated: paso por aquí");

        // Setear el onClick del boton Mas Info
        btn_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Abrir en el navegador el link
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(i);
            }
        });

        // Setear el onClick del boton Favorito
        btn_fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Ejecutar el query para comprobar si el pokemon esta en la lista de favoritos del usuario
                Cursor c = bd.rawQuery("SELECT * FROM Favoritos WHERE usuario=\'"+usuario+"\' AND pokemon=\'"+nombre+"\'" , null);

                // Comprobar si el pokemon esta en la lista de favoritos del usuario
                if (!c.moveToNext()) {

                    // Si el pokemon no esta en la lista añadirlo
                    bd.execSQL("INSERT INTO Favoritos(usuario,pokemon) VALUES(\'"+usuario+"\',\'"+nombre+"\')");
                    Log.i("Favoritos","Añadido a favoritos");

                } else {

                    // Si el pokemon esta en la lista eliminarlo
                    bd.execSQL("DELETE FROM Favoritos WHERE usuario=\'"+usuario+"\' AND pokemon=\'"+nombre+"\'");
                    Log.i("Favoritos","Borrado de favoritos");

                }
            }
        });

    }

    private void iniciarBD() {

        MiBD dbHelper = new MiBD(this.getActivity(),"pokedex",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bd = db;

    }

    // Cambiar la imagen del Pokemon de acuerdo a su nombre
    private void setImage(){

        Log.i("switch","Pasamos por el switch");

        switch (nombre){

            case "Charmander": {

//                String mDrawableName = nombre;
//                int resID = contexto.getResources().getIdentifier(mDrawableName , "drawable", contexto.getPackageName());
                iv_img.setImageResource(R.drawable.charmander);
                break;
            }

            case "Bulbasaur": {

                iv_img.setImageResource(R.drawable.bulbasaur);
                break;

            }

            case "Squirtle": {

                iv_img.setImageResource(R.drawable.squirtle);
                break;

            }

        }

    }

    // Actualizar los parametros de acuerdo a los recibidos
    public void actualizarDatos(String n, String tp1, String tp2){

        Log.d("prueba", "actualizarDatos: llego aquí");

        if (tv_nombre != null) {
            tv_nombre.setText(n);
            tv_tipo1.setText(tp1);
            tv_tipo2.setText(tp2);
            int resource = getResources().getIdentifier(nombre, "drawable", getContext().getPackageName());
            //Drawable res = getResources().getDrawable(resource);
            //iv_img.setImageDrawable(res);
            iv_img.setImageResource(resource);
        }
        else {
            // Se añade este else porque se puede acceder a actualizarDatos antes de obtener el View en onCreateView
            // Esto ocurre cuando pasamos de la primera a la segunda actividad en modo portrait
            Log.d("prueba", "actualizarDatos: entro al else");
            nombre = n;
            tipo1 = tp1;
            tipo2 = tp2;
        }

    }

}
