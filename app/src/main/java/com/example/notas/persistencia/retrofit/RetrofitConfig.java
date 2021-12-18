package com.example.notas.persistencia.retrofit;

import com.example.notas.persistencia.RecordatorioDataSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static RecordatorioRest recordatorio = null;

    private static void init(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://recordatorio-api.duckdns.org/recordatorio")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recordatorio = retrofit.create(RecordatorioRest.class);
    }

    public static RecordatorioRest getRecordatorioRest(){
        if(recordatorio == null) init();
        return recordatorio;
    }
}
