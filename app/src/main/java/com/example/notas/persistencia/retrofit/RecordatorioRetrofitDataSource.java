package com.example.notas.persistencia.retrofit;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.RecordatorioDataSource;
import com.example.notas.persistencia.room.MyRoomDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordatorioRetrofitDataSource implements RecordatorioDataSource {

    private static RetroRest rr;

    public RecordatorioRetrofitDataSource(Context ctx) {
        if(rr == null){
            rr = new RetroRest();
        }
    }

    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {

        RecordatorioModel record = rr.guardarRecordatorio(recordatorio);
        callback.resultado(record==null);

    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {

        List<RecordatorioModel> lista = rr.listarTodos();

        if(lista==null){
            callback.resultado(false,new ArrayList<>());
        }
        else{
            callback.resultado(true,lista);
        }
    }
}
