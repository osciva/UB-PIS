package com.example.pisprojecte;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pisprojecte.Entidad.AdaptadorBase;
import com.example.pisprojecte.Entidad.Base;

import java.util.ArrayList;

public class ListadoBasesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView barraBusquedaBases;
    RecyclerView listadoBases;
    Button back_listado;
    Button add_base;
    MediaPlayer mediaPlayer;
    ArrayList<Base> arrayBases;
    AdaptadorBase adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_bases);
        barraBusquedaBases = findViewById(R.id.buscar_letra);
        listadoBases = findViewById(R.id.recycler_listado_bases);
        back_listado = findViewById(R.id.back_listado);
        add_base = findViewById(R.id.add_base);
        //mediaPlayer = MediaPlayer.create(this, )

        listadoBases.setLayoutManager(new LinearLayoutManager(this));

        arrayBases = new ArrayList<>();
        arrayBases.add(new Base("Canción 1", "sonidoUno"));
        arrayBases.add(new Base("Canción 2", "sonidoDos"));
        arrayBases.add(new Base("Canción 3", "sonidoTres"));
        arrayBases.add(new Base("Canción 4", "sonidoCuatro"));

        adapter = new AdaptadorBase(ListadoBasesActivity.this, arrayBases);
        listadoBases.setAdapter(adapter);

        back_listado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoBasesActivity.this, InicioActivity.class);
                startActivity(intent);
                /*if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                 */

            }
        });

        add_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        barraBusquedaBases.setOnQueryTextListener(this);

    }


    public void agregarBaseDeDispositivo(View view){

    }

    public void searchBasesPorNombre(View view){

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }
}


