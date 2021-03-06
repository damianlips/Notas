package com.example.notas.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "TABLA_RECORDATORIOS")
public class RecordatorioModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID_RECORDATORIO")
    private String id;

    @SerializedName("mensaje")
    private String texto;
    private Date fecha;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public RecordatorioModel(){
        this.id= UUID.randomUUID().toString();
    }
    public RecordatorioModel(final String texto, final Date fecha) {
        this.id= UUID.randomUUID().toString();
        this.texto = texto;
        this.fecha = fecha;
    }
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !getClass().equals(other.getClass())) {
            return false;
        }
        final RecordatorioModel that = (RecordatorioModel) other;
        return Objects.equals(this.texto, that.texto) && Objects.equals(this.fecha, that.fecha);
    }
    @Override
    public int hashCode() {
        return Objects.hash(texto) + Objects.hash(fecha);
    }

    @Override
    public String toString() {

        StringBuilder strb = new StringBuilder();
        strb.append("(Programado para ");
        if(fecha.getDate()<10) strb.append('0');
        strb.append(fecha.getDate());
        strb.append('/');
        if(fecha.getMonth()<9) strb.append('0');
        strb.append(fecha.getMonth()+1);
        strb.append('/');
        strb.append(fecha.getYear()+1900);
        strb.append(" a las ");
        if(fecha.getHours()<10) strb.append('0');
        strb.append(fecha.getHours());
        strb.append(':');
        if(fecha.getMinutes()<10) strb.append('0');
        strb.append(fecha.getMinutes());
        strb.append(')');
        strb.append(System.getProperty("line.separator"));
        strb.append(texto);
        return strb.toString();
    }
}
