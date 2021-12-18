package com.example.notas.persistencia.retrofit;

import com.example.notas.model.RecordatorioModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecordatorioRest {

    @GET
    Call<List<RecordatorioModel>> listarTodos();

    @POST
    Call<RecordatorioModel> crearRecordatorio(@Body RecordatorioModel r);

}
