package com.example.notas.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notas.R;
import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.RecordatorioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NotasExistentes extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView ld;

    private ListView creadas;
    private List<String> creados;
    private ArrayAdapter<String> adapterCreadas;

    RecordatorioRepository repositorio;
    String[] menu = {"Crear recordatorio", "Configuraciones"};
    private static final int CODIGO_CREAR_NOTA = 123;

    private void actualizarLista(){

        AsyncTask<Void,Void,String> at = new AsyncTask() {
            List<String> l;
            @Override
            protected Object doInBackground(Object[] objects) {
                List<RecordatorioModel> lista = new ArrayList<>();
                lista.addAll(repositorio.recuperarRecordatorios());
                l = lista.stream().map(c-> c.toString()).collect(Collectors.toList());
                return l;
            }

            @Override
            protected void onPostExecute(Object o) {
                creados = l;
                adapterCreadas.clear();
                adapterCreadas.addAll(creados);
                adapterCreadas.notifyDataSetChanged();
                super.onPostExecute(o);
            }
        };
        at.execute();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_existentes);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ld = findViewById(R.id.left_drawer);
        creadas = findViewById(R.id.creadas);
        repositorio = new RecordatorioRepository(getBaseContext());

        creados=new ArrayList<String>();
        adapterCreadas = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,creados);
        creadas.setAdapter(adapterCreadas);
        actualizarLista();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,menu);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        );
        ld.setAdapter(adapter);
        ld.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(NotasExistentes.this, CrearRecordatorio.class);
                        startActivityForResult(intent,CODIGO_CREAR_NOTA);
                        break;
                    case 1:
                        Intent intent2 = new Intent(NotasExistentes.this, Settings.class);
                        startActivity(intent2);
                        break;
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDrawerLayout.closeDrawers();
        if(requestCode == CODIGO_CREAR_NOTA){
            if(resultCode == Activity.RESULT_OK){
                actualizarLista();
                Toast.makeText(NotasExistentes.this, "El recordatorio se creo correctamente", Toast.LENGTH_LONG).show();
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                if(data!=null){
                    Toast.makeText(NotasExistentes.this, "No se pudo guardar el recordatorio correctamente", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}