package com.example.notas.persistencia.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.notas.model.RecordatorioModel;


@Dao
public interface RecordatorioDAO {

    @Insert
    public void nuevoRecordatorio(RecordatorioModel r);

    @Update
    public void actualizarRecordatorio(RecordatorioModel r);

    @Delete
    public void borrarRecordatorio(RecordatorioModel r);


    @Query("SELECT * FROM TABLA_RECORDATORIOS")
    public RecordatorioModel[] cargarRecordatorios();
}
