package com.example.notas.persistencia;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.retrofit.RecordatorioRetrofitDataSource;
import com.example.notas.persistencia.room.RecordatorioRoomDataSource;
import com.example.notas.persistencia.sharedpreferences.RecordatorioPreferencesDataSource;

import java.util.ArrayList;
import java.util.List;

public class RecordatorioRepository {
    private static RecordatorioDataSource datasource;

    public void actualizarTipo(Context ctx){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        int tipo = Integer.valueOf(sharedPreferences.getString("tipo_persistencia","0"));

        switch (tipo){
            case 0:
                this.datasource = new RecordatorioPreferencesDataSource(ctx);
                break;
            case 1:
                this.datasource = new RecordatorioRoomDataSource(ctx);
                break;
            case 2:
                this.datasource = new RecordatorioRetrofitDataSource(ctx);
                break;

        }
    }

    public RecordatorioRepository(Context ctx){

        actualizarTipo(ctx);

    }

    public RecordatorioRepository(final RecordatorioDataSource datasource) {
        this.datasource = datasource;
    }
// Metodos que recuperan los recordatorios usando el data source

    boolean salida;
    public boolean guardarRecordatorio(RecordatorioModel recordatorio) {
        datasource.guardarRecordatorio(recordatorio, new RecordatorioDataSource.GuardarRecordatorioCallback() {
            @Override
            public void resultado(boolean exito) {
                salida= exito;
            }
        });
        return salida;
    }


    public List<RecordatorioModel> recuperarRecordatorios(){
        final List<RecordatorioModel> lista = new ArrayList<>();
        boolean resultado;

        datasource.recuperarRecordatorios(new RecordatorioDataSource.RecuperarRecordatorioCallback() {
            @Override
            public void resultado(boolean exito, List<RecordatorioModel> recordatorios) {
                if(exito){
                    salida = exito;
                    lista.addAll(recordatorios);
                }
            }
        });

        return lista;
    }




}
