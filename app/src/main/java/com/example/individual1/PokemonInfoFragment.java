package com.example.individual1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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

        Log.i("Recorrido","Paso por onActivityCreated PokemonInfoFragment");
        // Recoger los datos que le hemos pasado desde ListaPokemon
        Intent iin = this.getActivity().getIntent();
        Bundle b = iin.getExtras();
        nombre = (String) b.get("nombre");
        tipo1 = (String) b.get("tipo1");
        tipo2 = (String) b.get("tipo2");
        usuario = (String) b.get("user");
        link = "https://www.wikidex.net/wiki/"+nombre;

        iniciarBD();

        iniciarLayout();

        setImage();

        Log.d("prueba", "onActivityCreated PokemonListFragment: paso por aquí");

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

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // Comprobar si el pokemon esta en la lista de favoritos del usuario
                if (!c.moveToNext()) {

                    // Crear un dialogo para que el usuario confirme el añadir el pokemon a favoritos
                    builder.setMessage("¿Quieres añadir el pokemon " + nombre + " a tu lista de favoritos?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // Ejecutar el query para añadir el pokemon a la lista de favoritos
                                    bd.execSQL("INSERT INTO Favoritos(usuario,pokemon) VALUES(\'"+usuario+"\',\'"+nombre+"\')");
                                    Log.i("Favoritos","Añadido a favoritos");

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // No hacer nada, cerrar el dialogo
                                    dialog.cancel();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Añadir a favoritos");
                    alert.show();

                } else {

                    // Crear un dialogo para que el usuario confirme el borrar el pokemon de favoritos
                    builder.setMessage("¿Quieres eliminar el pokemon " + nombre + " de tu lista de favoritos?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // Ejecutar el query para eliminar el pokemon de la lista de favoritos
                                    bd.execSQL("DELETE FROM Favoritos WHERE usuario=\'"+usuario+"\' AND pokemon=\'"+nombre+"\'");
                                    Log.i("Favoritos","Borrado de favoritos");

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // No hacer nada, cerrar el dialogo
                                    dialog.cancel();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Borrar de favoritos");
                    alert.show();

                }

                c.close();
            }
        });

    }

    public void iniciarLayout() {

        Log.i("Recorrido","Paso por iniciarLayout PokemonInfoFragment");
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // Cambiar los elementos del layout LANDSCAPE a corde a los parametros recividos
            tv_nombre= getView().findViewById(R.id.nombreL);
            tv_tipo1= getView().findViewById(R.id.tipo1L);
            tv_tipo2= getView().findViewById(R.id.tipo2L);
            btn_info=getView().findViewById(R.id.infoL);
            btn_fav=getView().findViewById(R.id.favsL);
            iv_img=getView().findViewById(R.id.imageViewL);
            tv_nombre.setText(nombre);
            tv_tipo1.setText(tipo1);
            tv_tipo2.setText(tipo2);

        } else {

            // Cambiar los elementos del layout PORTRAIT a corde a los parametros recividos
            tv_nombre= getView().findViewById(R.id.nombre);
            tv_tipo1= getView().findViewById(R.id.tipo1);
            tv_tipo2= getView().findViewById(R.id.tipo2);
            btn_info=getView().findViewById(R.id.info);
            btn_fav=getView().findViewById(R.id.favs);
            iv_img=getView().findViewById(R.id.imageView);
            tv_nombre.setText(nombre);
            tv_tipo1.setText(tipo1);
            tv_tipo2.setText(tipo2);

        }

    }

    private void iniciarBD() {

        Log.i("Recorrido","Paso por iniciarBD PokemonInfoFragment");
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

}
