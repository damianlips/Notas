package com.example.notas.persistencia.retrofit;

import android.util.Log;

import com.example.notas.model.RecordatorioModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRest {
    public RecordatorioModel guardarRecordatorio(RecordatorioModel r){
        RecordatorioRest recordatorioRest = RetrofitConfig.getRecordatorioRest();
        Call<RecordatorioModel> llamada = recordatorioRest.crearRecordatorio(r);
        llamada.enqueue(new Callback<RecordatorioModel>(){
            @Override
            public void onResponse(Call<RecordatorioModel> call, Response<RecordatorioModel> response) {
                Log.d("RETROFIT_ANDROID__", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<RecordatorioModel> call, Throwable t) {

            }
        });
        return null; // por ahora
    };
}
