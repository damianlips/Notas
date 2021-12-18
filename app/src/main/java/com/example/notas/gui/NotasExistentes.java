package com.example.notas.gui;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    private static final int CODIGO_SETTINGS = 222;

    private void actualizarLista(){

        AsyncTask<Void,Void,String> at = new AsyncTask() {
            List<String> l;
            @Override
            protected Object doInBackground(Object[] objects) {
                l = new ArrayList<String>();
                List<RecordatorioModel> lista = new ArrayList<>();
                lista.addAll(repositorio.recuperarRecordatorios());
                for(RecordatorioModel r : lista){
                    l.add(r.toString());
                }
//                l = lista.stream().map(c-> c.toString()).collect(Collectors.toList());
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 2.
        // the default behaviour for ActionBarDrawerToggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == android.R.id.home) {
            // 3.
            // here you can define your custom click listener / onClick method
            // for ActionBarDrawerToggle
           // String customClick = getResources().getString(R.string.custom_click_event);
            //Toast.makeText(this, customClick, Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_existentes);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ld = findViewById(R.id.left_drawer);
        creadas = findViewById(R.id.creadas);
        repositorio = new RecordatorioRepository(getBaseContext());
        creados=new ArrayList<String>();
        adapterCreadas = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,creados);
        creadas.setAdapter(adapterCreadas);
        actualizarLista();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        ){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("c");
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("a");
            }

        };
        //mDrawerToggle.syncState();

        mDrawerLayout.addDrawerListener(mDrawerToggle);



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
                        startActivityForResult(intent2,CODIGO_SETTINGS);
                        repositorio.actualizarTipo(getBaseContext());
                        actualizarLista();
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
        else{
            if(requestCode == CODIGO_SETTINGS){
                repositorio = new RecordatorioRepository(getBaseContext());
                repositorio.actualizarTipo(getBaseContext());
                actualizarLista();
            }
        }
    }
}