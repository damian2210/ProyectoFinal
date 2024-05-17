package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Adaptadores.SucursalAdaptador;
import com.example.myapplication.Objetos.ObjEmpleado;
import com.example.myapplication.Objetos.ObjSucursal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Visualizar extends AppCompatActivity {

    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ArrayList<ObjSucursal> sucursales = new ArrayList<>();
        String clase = null;
        Intent intent = getIntent();
        Button salir=findViewById(R.id.btnAtrasVer);
        if (intent != null) {
           clase=intent.getStringExtra("clase");
            TextView titulo=findViewById(R.id.visualTitulo);
            titulo.setText("Visualizar "+clase);
        }
        if(clase.equals("sucursal")) {
            conseguirListaSucursal(sucursales);
            ListView miLista = (ListView) findViewById(R.id.listaGeneral);
            SucursalAdaptador miAdaptador = new SucursalAdaptador(this, sucursales);

            miLista.setAdapter(miAdaptador);
        }
        String finalClase = clase;
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalClase.equals("sucursal")){
                    Intent cambio=new Intent(v.getContext(),Sucursal.class);
                    startActivity(cambio);
                }
            }
        });
    }

    public void conseguirListaSucursal(ArrayList<ObjSucursal> sucursales) {
        String uri= Uri.parse("http://10.0.2.2:8080/sucursal/listarSucursales").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Fallo","Error en petición");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful() && response.body()!=null ){
                    String responseBody = response.body().string();
                    Gson gson = new Gson();


                    Type listType = new TypeToken<ArrayList<ObjSucursal>>() {}.getType();
                    ArrayList<ObjSucursal> listaobjetos = gson.fromJson(responseBody, listType);
                    for (ObjSucursal sucursal : listaobjetos) {
                        sucursales.add(sucursal);
                    }


                }else{
                    Log.d("Fallo","Error");
                }

            }
        });

    }
    }


