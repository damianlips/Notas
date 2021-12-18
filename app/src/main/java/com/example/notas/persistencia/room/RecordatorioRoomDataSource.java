package com.example.notas.persistencia.room;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.RecordatorioDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordatorioRoomDataSource implements RecordatorioDataSource {

    private static MyRoomDB roomdb=null;
    private static RecordatorioDAO roomdao=null;

    public RecordatorioRoomDataSource(Context ctx) {
        if(roomdb == null){
            roomdb = Room.databaseBuilder(ctx,MyRoomDB.class,"roomdb").fallbackToDestructiveMigration().build();
            roomdao = roomdb.getRecordatorioDAO();
        }
    }


    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {
        Runnable insertFila = () -> {
            roomdao.nuevoRecordatorio(recordatorio);
            callback.resultado(true);
        };
        Thread hilo = new Thread(insertFila);
        hilo.start();
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {
        List<RecordatorioModel> recordatorios = new ArrayList<>();
        Runnable obtenerFilas = () -> {
            recordatorios.addAll(Arrays.asList(roomdao.cargarRecordatorios()));
            callback.resultado(true,recordatorios);
        };
        Thread hilo = new Thread(obtenerFilas);
        hilo.start();
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
