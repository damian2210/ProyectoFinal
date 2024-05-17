package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Objetos.ObjEmpleado;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText txtusuario=findViewById(R.id.txtUsuarioLog);
        EditText txtcontra=findViewById(R.id.txtContraLog);
        Button login=findViewById(R.id.btnlogLog);
        Button guardar=findViewById(R.id.btnGuardarLog);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario=txtusuario.getText().toString();
                String contra=txtcontra.getText().toString();
                String uri= Uri.parse("http://10.0.2.2:8080/empleado/buscarUsuario").buildUpon()
                        .appendQueryParameter("usuario",usuario)
                        .appendQueryParameter("contraseña",contra)
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
                            String data=response.body().string();

                            Gson gson=new Gson();
                            ObjEmpleado e=gson.fromJson(data, ObjEmpleado.class);
                            Intent gestion=new Intent(getBaseContext(), Gestion.class);
                            gestion.putExtra("usuario",e.getUsuario());
                            gestion.putExtra("rol",e.getRol());
                            gestion.putExtra("cod",e.getCodEmpleado());
                            startActivity(gestion);
                        }else{
                            Log.d("Fallo","Usuario o contraseña incorrectos");
                        }

                    }
                });
            }
        });
    }
}