package com.example.notas.persistencia.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static RecordatorioRest recordatorioRest = null;
    private static OkHttpClient cliente;

    private static void init(){

        cliente = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("25407", "122334"))
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://recordatorio-api.duckdns.org/")
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recordatorioRest = retrofit.create(RecordatorioRest.class);
    }

    public static RecordatorioRest getRecordatorioRest(){
        if(recordatorioRest == null) init();
        return recordatorioRest;
    }
}
