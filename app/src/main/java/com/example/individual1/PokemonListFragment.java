package com.example.individual1;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class PokemonListFragment extends ListFragment {

    public interface listenerDelFragment{
        String[] cargarElementos();
        void seleccionarElemento(int position);
    }

    private listenerDelFragment elListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            elListener=(listenerDelFragment) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " + context.toString()
                    + "debe implementar listenerDelFragment");
        }
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] datos = elListener.cargarElementos();
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        elListener.seleccionarElemento(position);

    }

}
