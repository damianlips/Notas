package com.example.notas.persistencia.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.notas.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static RecordatorioRest recordatorioRest = null;
    private static OkHttpClient cliente;

    private static void init(Context ctx){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        String usuario = sharedPreferences.getString("user_sysacad","user");
        String contrasenia = sharedPreferences.getString("password_sysacad","password");


        cliente = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(usuario, contrasenia))
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://recordatorio-api.duckdns.org/")
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recordatorioRest = retrofit.create(RecordatorioRest.class);
    }

    public static RecordatorioRest getRecordatorioRest(Context ctx){
        init(ctx);
        return recordatorioRest;
    }
}
