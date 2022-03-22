package com.example.individual1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> pokemonList;
    int pokemones[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<String> ppokemonList, int[] ppokemones) {
        this.context = context;
        this.pokemonList = ppokemonList;
        this.pokemones = ppokemones;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return pokemonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.favoritos_list_view, null);
        TextView pokemon = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        pokemon.setText(pokemonList.get(i));
        setImages(icon, pokemon.getText().toString());
        return view;
    }

    // Cambiar la imagen del pokemon dependiendo del nombre de este
    private void setImages(ImageView icon, String nombre) {

        switch (nombre){

            case "Charmander": {

                icon.setImageResource(pokemones[0]);
                break;

            }

            case "Bulbasaur": {

                icon.setImageResource(pokemones[1]);
                break;

            }

            case "Squirtle": {

                icon.setImageResource(pokemones[2]);
                break;

            }

        }

    }

}