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
import com.example.myapplication.Objetos.ObjPrestar;
import com.example.myapplication.Objetos.ObjSucursal;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sucursal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sucursal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EditText txtcodSuc=findViewById(R.id.txtCodSuc);
        EditText txtdireccionSuc=findViewById(R.id.txtDirSuc);
        EditText txttlfSuc=findViewById(R.id.txtTlfSuc);

        OkHttpClient client=new OkHttpClient();
        Button insSuc=findViewById(R.id.btnInsSuc);
        Button modSuc=findViewById(R.id.btnModSuc);
        Button borrarSuc=findViewById(R.id.btnBorrarSuc);
        Button verSuc=findViewById(R.id.btnVisualizarSuc);


        verSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","sucursal");

                startActivity(gestion);
                finish();
            }
        });
        insSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = validarSuc(txtcodSuc,txttlfSuc,txtdireccionSuc);

                if (correcto == true) {
                    String codSuc = txtcodSuc.getText().toString();
                    String uri = Uri.parse("http://10.0.2.2:8080/sucursal/buscarSucursal").buildUpon()
                            .appendQueryParameter("id", codSuc)
                            .build().toString();
                    Request request = new Request.Builder()
                            .url(uri)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Sucursal.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Log.d("RESPUESTA",data.toString());
                                Gson gson=new Gson();
                                ObjSucursal sucursal=gson.fromJson(data, ObjSucursal.class);
                                if(sucursal!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Sucursal.this, "La sucursal ya existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                sucursal=new ObjSucursal(txtcodSuc.getText().toString(),txtdireccionSuc.getText().toString(),Integer.parseInt(txttlfSuc.getText().toString()));

                                insertarSucursal( client,sucursal);

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Sucursal.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        modSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = validarSuc(txtcodSuc,txttlfSuc,txtdireccionSuc);

                if (correcto == true) {
                    String codSuc = txtcodSuc.getText().toString();
                    String uri = Uri.parse("http://10.0.2.2:8080/sucursal/buscarSucursal").buildUpon()
                            .appendQueryParameter("id", codSuc)
                            .build().toString();
                    Request request = new Request.Builder()
                            .url(uri)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Sucursal.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Gson gson=new Gson();
                                ObjSucursal sucursal =gson.fromJson(data, ObjSucursal.class);
                                if(sucursal==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Sucursal.this, "La sucursal no existe", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    });
                                    return;
                                }
                                sucursal=new ObjSucursal(txtcodSuc.getText().toString(),txtdireccionSuc.getText().toString(),Integer.parseInt(txttlfSuc.getText().toString()));
                                modSucursal(client,sucursal);
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Sucursal.this, "Error en respuesta", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        borrarSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = validarSuc(txtcodSuc,txttlfSuc,txtdireccionSuc);
                if (correcto == true) {
                    String codSuc = txtcodSuc.getText().toString();
                    String uri = Uri.parse("http://10.0.2.2:8080/sucursal/buscarSucursal").buildUpon()
                            .appendQueryParameter("id", codSuc)
                            .build().toString();
                    Request request = new Request.Builder()
                            .url(uri)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Sucursal.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                String data = response.body().string();

                                Gson gson = new Gson();
                                ObjSucursal sucursal = gson.fromJson(data, ObjSucursal.class);
                                if (sucursal == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Sucursal.this, "La sucursal no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    return;
                                }




                                String uri = Uri.parse("http://10.0.2.2:8080/prestar/buscarSucursalEnPrestamo").buildUpon()
                                        .appendQueryParameter("id",sucursal.getCodSucursal())
                                        .build().toString();
                                Request request = new Request.Builder()
                                        .url(uri)
                                        .build();

                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                        Log.d("Fallo","Error en petición");
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();
                                            Log.d("Fallo", data.toString());
                                            Gson gson=new Gson();
                                            ObjPrestar prestar =gson.fromJson(data, ObjPrestar.class);
                                            if(prestar!=null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Sucursal.this, "La sucursal realiza o recibe un prestamo,no se puede borrar", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }

                                            borrarSucursal(client, sucursal);

                                        }else{
                                            Log.d("Fallo","Error");
                                        }
                                    }
                                });


                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Sucursal.this, "Error en respuesta", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

            }
        }
        });

    }

    public void insertarSucursal( OkHttpClient client,ObjSucursal sucursal){
        Gson gson = new Gson();
        String json = gson.toJson(sucursal);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse("http://10.0.2.2:8080/sucursal/insertar").buildUpon()
                .build().toString();
        Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Sucursal.this, "No insertado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, "No insertado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modSucursal( OkHttpClient client,ObjSucursal sucursal){
        Gson gson = new Gson();
        String json = gson.toJson(sucursal);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse("http://10.0.2.2:8080/sucursal/modificar").buildUpon()
                .build().toString();
        Request request = new Request.Builder()
                .url(uri)
                .put(body)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Sucursal.this, "No modificado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, "No modificado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void borrarSucursal( OkHttpClient client,ObjSucursal sucursal){
        Gson gson = new Gson();
        String json = gson.toJson(sucursal);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse("http://10.0.2.2:8080/sucursal/borrar").buildUpon()
                .build().toString();
        Request request = new Request.Builder()
                .url(uri)
                .delete(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Sucursal.this, "No borrado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, "No borrado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }


    private boolean validarSuc(EditText txtcodSuc, EditText txttlfSuc,EditText txtDirSuc){

        if (txtcodSuc.getText().toString().trim().isEmpty() == true || txtcodSuc.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir el código", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile("^s\\d{2}$");
            boolean correcto = p.matcher(txtcodSuc.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this, "Formato de código incorrecto(Formato válido:s+dos dígitos", Toast.LENGTH_SHORT).show();
                txtcodSuc.setText(" ");
                return false;
            }
        }

        if (txtDirSuc.getText().toString().trim().isEmpty() == true || txtDirSuc.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir la direcion", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txttlfSuc.getText().toString().trim().isEmpty() == true||txttlfSuc.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el teléfono", Toast.LENGTH_SHORT).show();
            txttlfSuc.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{9}");
            boolean correcto = p.matcher(txttlfSuc.getText().toString()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de número de teléfono incorrecto", Toast.LENGTH_SHORT).show();
                txttlfSuc.requestFocus();
                txttlfSuc.setText(" ");
                return false;
            }
        }
        return true;
    }


}