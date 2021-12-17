package com.example.notas.persistencia;

import com.example.notas.model.RecordatorioModel;

import java.util.ArrayList;
import java.util.List;

public class RecordatorioRepository {
    private final RecordatorioDataSource datasource;
    public RecordatorioRepository(final RecordatorioDataSource datasource) {
        this.datasource = datasource;
    }
// Metodos que recuperan los recordatorios usando el data source


    public void guardarRecordatorio(RecordatorioModel recordatorio) {
        datasource.guardarRecordatorio(recordatorio, new RecordatorioDataSource.GuardarRecordatorioCallback() {
            @Override
            public void resultado(boolean exito) {

            }
        });
    }


    public List<RecordatorioModel> recuperarRecordatorios(){
        final List<RecordatorioModel> lista = new ArrayList<>();
        boolean resultado;

        datasource.recuperarRecordatorios(new RecordatorioDataSource.RecuperarRecordatorioCallback() {
            @Override
            public void resultado(boolean exito, List<RecordatorioModel> recordatorios) {
                if(exito){
                    lista.addAll(recordatorios);
                }
            }
        });

        return lista;
    }




}
