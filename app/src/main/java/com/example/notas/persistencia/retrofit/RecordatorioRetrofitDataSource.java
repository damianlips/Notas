package com.example.notas.persistencia.retrofit;

import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.RecordatorioDataSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordatorioRetrofitDataSource implements RecordatorioDataSource {

    @Override
    public void guardarRecordatorio(RecordatorioModel recordatorio, GuardarRecordatorioCallback callback) {

    }

    @Override
    public void recuperarRecordatorios(RecuperarRecordatorioCallback callback) {

    }
}
