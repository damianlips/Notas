package com.example.notas.persistencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.notas.model.RecordatorioModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RecordatorioPreferencesDataSource implements RecordatorioDataSource {
    private final SharedPreferences sharedPreferences;

    public RecordatorioPreferencesDataSource(final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {
        int cant = sharedPreferences.getInt("cantidad",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(cant+"texto",recordatorio.getTexto());
        editor.putString(cant+"fecha",recordatorio.getFecha().toString());
        editor.putInt("cantidad",cant+1);
        callback.resultado(editor.commit());

//        callback = new GuardarRecordatorioCallback() {
//            @Override
//            public void resultado(boolean exito) {
//
//            }
//        }
    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {

        List<RecordatorioModel> recordatorios = new ArrayList<>();

        int cant = sharedPreferences.getInt("cantidad",0);
        boolean exito = true;
        for (int i = 0; i < cant && exito; i++) {
            exito = sharedPreferences.contains(cant+"texto");
            if(exito){
                RecordatorioModel recordatorio = new RecordatorioModel();

                recordatorio.setTexto(sharedPreferences.getString(cant+"texto",""));
                recordatorio.setFecha(Date.valueOf(sharedPreferences.getString(cant+"fecha","")));
                recordatorios.add(recordatorio);
            }
        }

        callback.resultado(exito,recordatorios);
    }
// Implementación de los metodos de la interface
}
