package com.example.notas.gui;

import android.app.Activity;
import android.content.Intent;
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
import com.example.notas.persistencia.RecordatorioRepository;


public class NotasExistentes extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView ld;
    private ListView creadas;
    RecordatorioRepository repositorio;
    String[] menu = {"Crear recordatorio", "Configuraciones"};
    private static final int CODIGO_CREAR_NOTA = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_existentes);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ld = findViewById(R.id.left_drawer);
        creadas = findViewById(R.id.creadas);


        repositorio = new RecordatorioRepository(getBaseContext());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,menu);
        String[] creados = (String[]) this.repositorio.recuperarRecordatorios().stream()
                .map(c-> c.toString()).toArray(String[]::new)
                ;
        ArrayAdapter<String> adapterCreadas = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,creados);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        );
        ld.setAdapter(adapter);
        creadas.setAdapter(adapterCreadas);
        ld.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(NotasExistentes.this, MainActivity.class);
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
                String[] creados = (String[]) repositorio.recuperarRecordatorios().stream()
                        .map(c-> c.toString()).toArray(String[]::new)
                        ;
                ArrayAdapter<String> adapterCreadas = new ArrayAdapter<String>(NotasExistentes.this, android.R.layout.simple_list_item_1,creados);
                creadas.setAdapter(adapterCreadas);
                Toast.makeText(NotasExistentes.this, "El recordatorio se creo correctamente", Toast.LENGTH_LONG).show();
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(NotasExistentes.this, "No se pudo guardar el recordatorio correctamente", Toast.LENGTH_LONG).show();
            }
        }
    }
}