package com.rocio.poma.practica16_apirest;

//import android.telecom.Call;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface PaisesAPI {

    @GET("listar")
    Call<List<Paises>> getPaises();

    @POST("agregar")
    Call<Paises> agregar(@Body Paises pais);

    @PUT("modificar")
    Call<Paises> modificar(@Body Paises paise);

    @PUT("eliminar")
    Call<Paises> eliminar(@Body Paises paise);

}
