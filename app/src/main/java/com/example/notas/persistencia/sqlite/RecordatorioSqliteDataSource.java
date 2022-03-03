package com.example.notas.persistencia.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.RecordatorioDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordatorioSqliteDataSource  implements RecordatorioDataSource {

    private class DbHelper extends SQLiteOpenHelper{

        public DbHelper(final Context context) {
            super(context,"Recordatorios",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table recordatorios (_id integer primary key autoincrement, texto text, fecha integer);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS recordatorios");
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db,oldVersion,newVersion);
        }
    }

    private DbHelper helper;


    public RecordatorioSqliteDataSource(Context context) {
        this.helper = new DbHelper(context);
    }

    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("texto",recordatorio.getTexto());
        values.put("fecha",recordatorio.getFecha().getTime());
        long idNuevaFila = db.insert("recordatorios",null,values);
        callback.resultado(idNuevaFila!=-1);

    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {

        List<RecordatorioModel> recordatorios = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                "_id",
                "texto",
                "fecha"
        };

//        String selection = "texto LIKE ? ";
//        String selectionArgs[] = {"hol%"};
        Cursor cursor = db.query("recordatorios", projection, null,null,null,null,null);

//        Cursor cursor = db.rawQuery("SELECT _id, texto, fecha FROM recordatorios WHERE texto LIKE 'hol%'",null);

        while(cursor.moveToNext()){
            RecordatorioModel r = new RecordatorioModel();
            r.setId(String.valueOf(cursor.getInt(0)));
            r.setTexto(cursor.getString(1));
            r.setFecha(new Date(Long.valueOf(cursor.getInt(2))));

            recordatorios.add(r);
        }
        cursor.close();
        db.close();
        callback.resultado(true,recordatorios);
    }



}
