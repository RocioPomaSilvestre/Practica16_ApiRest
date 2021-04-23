package com.rocio.poma.practica16_apirest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    PaisesAPI paisesAPI;
    TextView jsonTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.uajms.edu.bo/blogdis413/wp-json/paises/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        paisesAPI = retrofit.create(PaisesAPI.class);
        jsonTextView=findViewById(R.id.jsonTxtView);
        
        listar();

        //AGREGAR
        FloatingActionButton btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paises paisNuevo=new Paises();
                paisNuevo.setId(315);
                paisNuevo.setNombre("BOLIVIA");
                Call<Paises> paisesCall=paisesAPI.agregar(paisNuevo);
                paisesCall.enqueue(new Callback<Paises>() {

                    @Override
                    public void onResponse(Call<Paises> call, Response<Paises> response) {
                        if (response.isSuccessful()){
                            String pais=jsonTextView.getText().toString();
                            pais=pais+response.body().getNombre()+"\n";
                            jsonTextView.setText(pais);
                            Toast.makeText(getApplicationContext(),"SE AGREGO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                            listar();
                        }
                    }
                    @Override
                    public void onFailure(Call<Paises> call, Throwable t) {

                    }
                });

            }
        });

        //MODIFICAR
        Button btnModificar =findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paises paisModificar=new Paises();
                paisModificar.setNombre("modificado");
                paisModificar.setId(200);
                Call<Paises> paisesCall=paisesAPI.modificar(paisModificar);
                paisesCall.enqueue(new Callback<Paises>() {
                    @Override
                    public void onResponse(Call<Paises> call, Response<Paises> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"MODIFICACION CORRECTA",Toast.LENGTH_SHORT).show();
                            listar();
                        }
                    }

                    @Override
                    public void onFailure(Call<Paises> call, Throwable t) {

                    }
                });
            }
        });

        //ELIMINAR
        Button btnEliminar=findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paises paisEliminar=new Paises();
                paisEliminar.setId(200);
                Call<Paises> paisesCall=paisesAPI.eliminar(paisEliminar);
                paisesCall.enqueue(new Callback<Paises>() {
                    @Override
                    public void onResponse(Call<Paises> call, Response<Paises> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"ELIMINACION CORRECTA CORRECTA",Toast.LENGTH_SHORT).show();
                            listar();
                        }
                    }

                    @Override
                    public void onFailure(Call<Paises> call, Throwable t) {

                    }
                });
            }
        });

    }


    public void listar(){

        Call<List<Paises>> paisesCall = paisesAPI.getPaises();

        paisesCall.enqueue(new Callback<List<Paises>>() {
            @Override
            public void onResponse(Call<List<Paises>> call, Response<List<Paises>> response) {
                if (response.isSuccessful()){
                    List<Paises> paisesList=response.body();
                    Iterator<Paises> paisesIt= paisesList.iterator();
                    String paises="";
                    while (paisesIt.hasNext()){
                        Paises pais=paisesIt.next();
                        paises=paises+pais.getNombre()+"\n";
                    }
                    jsonTextView.setText(paises);
                }
            }

            @Override
            public void onFailure(Call<List<Paises>> call, Throwable t) {

            }
        });

    }
}

