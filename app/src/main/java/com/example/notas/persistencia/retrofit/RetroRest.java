package com.example.notas.persistencia.retrofit;

import android.util.Log;

import com.example.notas.model.RecordatorioModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRest {

    private RecordatorioModel data;
    private List<RecordatorioModel> datalista;
    private RecordatorioRest recordatorioRest;
    public RetroRest() {
        this.data = null;
        this.datalista = null;
        recordatorioRest = RetrofitConfig.getRecordatorioRest();
    }

    public RecordatorioModel guardarRecordatorio(RecordatorioModel r){

        Call<RecordatorioModel> llamada = recordatorioRest.crearRecordatorio(r.getTexto(),r.getFecha());
        llamada.enqueue(new Callback<RecordatorioModel>(){
            @Override
            public void onResponse(Call<RecordatorioModel> call, Response<RecordatorioModel> response) {
//                Log.d("RETROFIT_ANDROID__", "onResponse: " + response.body());
                switch (response.code()) {
                    case 200:
                       data = response.body();
                        break;
                    case 401:
                    case 403:
                    case 500:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<RecordatorioModel> call, Throwable t) {

            }
        });




        return data;
    };

    public List<RecordatorioModel> listarTodos(){

        Call<List<RecordatorioModel>> llamada = recordatorioRest.listarTodos();
        llamada.enqueue(new Callback<List<RecordatorioModel>>(){
            @Override
            public void onResponse(Call<List<RecordatorioModel>> call, Response<List<RecordatorioModel>> response) {
                switch (response.code()) {
                    case 200:
                        datalista = response.body();
                        break;
                    case 401:
                    case 403:
                    case 500:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<RecordatorioModel>> call, Throwable t) {

            }

        });
        return datalista;
    };


}
