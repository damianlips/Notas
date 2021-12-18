package com.example.notas.persistencia.retrofit;

import com.example.notas.model.RecordatorioModel;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecordatorioRest {

    @GET("recordatorio/")
    Call<List<RecordatorioModel>> listarTodos();

    @POST("recordatorio/")
    @FormUrlEncoded
    Call<RecordatorioModel> crearRecordatorio(@Field("mensaje") String texto, @Field("fecha") Date fecha);
    //    Call<RecordatorioModel> crearRecordatorio(@Body RecordatorioModel r);
}
