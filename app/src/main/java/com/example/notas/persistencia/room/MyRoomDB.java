package com.example.notas.persistencia.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.notas.model.RecordatorioModel;

@Database(entities = {RecordatorioModel.class},version = 1)
@TypeConverters({Converters.class})
public abstract class MyRoomDB extends RoomDatabase {


    public abstract RecordatorioDAO getRecordatorioDAO();


}
