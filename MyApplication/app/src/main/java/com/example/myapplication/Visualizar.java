package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Adaptadores.ClienteAdaptador;
import com.example.myapplication.Adaptadores.EmpleadoAdaptador;
import com.example.myapplication.Adaptadores.PrestarAdaptador;
import com.example.myapplication.Adaptadores.ProductoAdaptador;
import com.example.myapplication.Adaptadores.SucursalAdaptador;
import com.example.myapplication.Adaptadores.VenderAdaptador;
import com.example.myapplication.Objetos.ObjCliente;
import com.example.myapplication.Objetos.ObjEmpleado;
import com.example.myapplication.Objetos.ObjPrestar;
import com.example.myapplication.Objetos.ObjProducto;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.Objetos.ObjVender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        ListView miLista = (ListView) findViewById(R.id.listaGeneral);
        String clase = null;
        Intent intent = getIntent();
        Button salir=findViewById(R.id.btnAtrasVer);
        if (intent != null) {
           clase=intent.getStringExtra("clase");
            TextView titulo=findViewById(R.id.visualTitulo);
            titulo.setText("Visualizar "+clase);
        }
        switch (clase){
            case "sucursal":
                ArrayList<ObjSucursal> sucursales = new ArrayList<>();
                conseguirListaSucursal(sucursales);

                SucursalAdaptador sucursalAdaptador = new SucursalAdaptador(this, sucursales);

                miLista.setAdapter(sucursalAdaptador);
                break;
            case "empleado":
                ArrayList<ObjEmpleado> empleados = new ArrayList<>();
                conseguirListaEmpleados(empleados);

                EmpleadoAdaptador empleadoAdaptador = new EmpleadoAdaptador(this, empleados);

                miLista.setAdapter(empleadoAdaptador);
                break;
            case "cliente":
                ArrayList<ObjCliente> clientes = new ArrayList<>();
                conseguirListaClientes(clientes);

                ClienteAdaptador clienteAdaptador = new ClienteAdaptador(this, clientes);

                miLista.setAdapter(clienteAdaptador);
                break;
            case "vender":
                ArrayList<ObjVender> ventas = new ArrayList<>();
                conseguirListaVentas(ventas,miLista);

                VenderAdaptador venderAdaptador = new VenderAdaptador(this, ventas);

                miLista.setAdapter(venderAdaptador);
                break;
            case "producto":
                ArrayList<ObjProducto> productos = new ArrayList<>();
                conseguirListaProductos(productos);

                ProductoAdaptador productoAdaptador = new ProductoAdaptador(this, productos);

                miLista.setAdapter(productoAdaptador);
                break;
            case "prestar":
                ArrayList<ObjPrestar> prestamos = new ArrayList<>();
                conseguirListaPrestamos(prestamos);

                PrestarAdaptador prestarAdaptador = new PrestarAdaptador(this, prestamos);

                miLista.setAdapter(prestarAdaptador);
                break;

        }


        String finalClase = clase;
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambio;
                switch (finalClase){
                    case "sucursal":
                        cambio=new Intent(v.getContext(),Sucursal.class);
                        startActivity(cambio);
                        finish();
                        break;
                    case "empleado":
                        cambio=new Intent(v.getContext(),Empleado.class);
                        startActivity(cambio);
                        finish();
                        break;
                    case "cliente":
                        cambio=new Intent(v.getContext(),Cliente.class);
                        startActivity(cambio);
                        finish();
                        break;
                    case "vender":
                        cambio=new Intent(v.getContext(),Vender.class);
                        startActivity(cambio);
                        finish();
                        break;
                    case "producto":
                        cambio=new Intent(v.getContext(),Producto.class);
                        startActivity(cambio);
                        finish();
                        break;
                    case "prestar":
                        cambio=new Intent(v.getContext(),Prestar.class);
                        startActivity(cambio);
                        finish();
                        break;

                }
            }
        });
    }

    public void conseguirListaSucursal(ArrayList<ObjSucursal> sucursales) {
        datos d=getDatos();
        String uri= Uri.parse(d.getUrl() + "/sucursal/listarSucursales").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                    }
                });

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Visualizar.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    public void conseguirListaEmpleados(ArrayList<ObjEmpleado> empleados) {
        datos d=getDatos();
        String uri= Uri.parse(d.getUrl() + "/empleado/listarEmpleados").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Visualizar.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful() && response.body()!=null ){
                    String responseBody = response.body().string();
                    Gson gson = new Gson();


                    Type listType = new TypeToken<ArrayList<ObjEmpleado>>() {}.getType();
                    ArrayList<ObjEmpleado> listaobjetos = gson.fromJson(responseBody, listType);
                    for (ObjEmpleado empleado : listaobjetos) {
                        empleados.add(empleado);
                    }


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Visualizar.this, "Error ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    public void conseguirListaProductos(ArrayList<ObjProducto> productos ) {
        datos d=getDatos();
        String uri= Uri.parse(d.getUrl() + "/producto/listarProductos").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful() && response.body()!=null ){
                    String responseBody = response.body().string();
                    Log.d("RESPUESTA",responseBody.toString());
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();


                    Type listType = new TypeToken<ArrayList<ObjProducto>>() {}.getType();
                    ArrayList<ObjProducto> listaobjetos = gson.fromJson(responseBody, listType);
                    for (ObjProducto producto : listaobjetos) {
                        productos.add(producto);
                    }


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Visualizar.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    public void conseguirListaClientes(ArrayList<ObjCliente> clientes ) {
        datos d=getDatos();
        String uri= Uri.parse(d.getUrl() + "/cliente/listarClientes").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful() && response.body()!=null ){
                    String responseBody = response.body().string();
                    Gson gson = new Gson();


                    Type listType = new TypeToken<ArrayList<ObjCliente>>() {}.getType();
                    ArrayList<ObjCliente> listaobjetos = gson.fromJson(responseBody, listType);
                    for (ObjCliente cliente : listaobjetos) {
                        clientes.add(cliente);
                    }


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }

    public void conseguirListaVentas(ArrayList<ObjVender> ventas ,ListView miLista) {
        datos d=getDatos();
        String uri= Uri.parse(d.getUrl() + "/vender/listarVentas").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful() && response.body()!=null ){
                    String responseBody = response.body().string();
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();


                    Type listType = new TypeToken<ArrayList<ObjVender>>() {}.getType();
                    ArrayList<ObjVender> listaobjetos = gson.fromJson(responseBody, listType);
                    for (ObjVender vender : listaobjetos) {
                        ventas.add(vender);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ArrayAdapter) miLista.getAdapter()).notifyDataSetChanged();
                        }
                    });


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    public void conseguirListaPrestamos(ArrayList<ObjPrestar> prestamos ) {
        datos d=getDatos();
        String uri= Uri.parse(d.getUrl() + "/prestar/listarPrestars").buildUpon()
                .build().toString();
        Request request=new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful() && response.body()!=null ){
                    String responseBody = response.body().string();
                    Gson gson = new Gson();


                    Type listType = new TypeToken<ArrayList<ObjPrestar>>() {}.getType();
                    ArrayList<ObjPrestar> listaobjetos = gson.fromJson(responseBody, listType);
                    for (ObjPrestar prestar : listaobjetos) {
                        prestamos.add(prestar);
                    }


                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Visualizar.this, "Error en petición", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    private datos getDatos(){
        SharedPreferences preferencias= getBaseContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        preferenceHelper helper=new preferenceHelper(preferencias);
        datos d=helper.cargar();
        return d;
    }
    }


