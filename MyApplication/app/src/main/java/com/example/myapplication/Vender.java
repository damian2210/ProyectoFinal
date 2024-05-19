package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Objetos.ObjCliente;
import com.example.myapplication.Objetos.ObjEmpleado;
import com.example.myapplication.Objetos.ObjPrestar;
import com.example.myapplication.Objetos.ObjProducto;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.Objetos.ObjVender;
import com.example.myapplication.Objetos.VenderPK;
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.sql.Date;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Vender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vender);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cambiarTamaño();
        String usuario = null;
        String rol = null;
        Intent intent = getIntent();


        EditText txtCodCliVend=findViewById(R.id.txtCodCliVend);
        EditText txtCodProVend=findViewById(R.id.txtCodProVend);
        EditText txtCodEmpVend=findViewById(R.id.txtCodEmpVend);
        EditText txtFechaVend=findViewById(R.id.txtFechaVend);

        OkHttpClient client=new OkHttpClient();
        Button insVend=findViewById(R.id.btnInsVend);
        Button modVend=findViewById(R.id.btnModVend);
        Button verVend=findViewById(R.id.btnVisualizarVend);
        Button salirVend=findViewById(R.id.btnSalirVend);
        ImageButton ajustes=findViewById(R.id.btnImagenVend);


        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes=new Intent(getBaseContext(), Config.class);
                startActivity(ajustes);
                finish();
            }
        });
        if (intent != null) {
            usuario = intent.getStringExtra("usuario");
            rol = intent.getStringExtra("rol");


        }
        String usuario2=usuario;
        String rol2=rol;
        salirVend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir=new Intent(getBaseContext(), Gestion.class);
                salir.putExtra("usuario",usuario2);
                salir.putExtra("rol",rol2);
                startActivity(salir);
                finish();
            }
        });


        verVend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","vender");

                startActivity(gestion);
                finish();
            }
        });
        insVend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarVend(txtCodCliVend,txtCodProVend,txtCodEmpVend,txtFechaVend);

                if (correcto == true) {
                    String codCli = txtCodCliVend.getText().toString();
                    String codPro = txtCodProVend.getText().toString();
                    String codEmp=txtCodEmpVend.getText().toString();
                    String uri = Uri.parse(d.getUrl() + "/vender/buscarVenta").buildUpon()
                            .appendQueryParameter("id", codPro)
                            .appendQueryParameter("id2", codCli)
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
                                    Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();

                                Gson gson = new GsonBuilder()
                                        .setDateFormat("yyyy-MM-dd")
                                        .create();
                                ObjVender vender=gson.fromJson(data, ObjVender.class);
                                if(vender!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Vender.this, "La venta ya existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/empleado/buscarEmpleado").buildUpon()
                                        .appendQueryParameter("id", codEmp)
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
                                                Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();

                                            Gson gson=new Gson();
                                            ObjEmpleado empleado=gson.fromJson(data, ObjEmpleado.class);
                                            if(empleado==null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Vender.this, "El empleado no existe", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                return;
                                            }
                                            String uri = Uri.parse(d.getUrl() + "/cliente/buscarCliente").buildUpon()
                                                    .appendQueryParameter("id", codCli)
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
                                                            Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                    if (response.isSuccessful()) {
                                                        String data = response.body().string();

                                                        Gson gson = new Gson();
                                                        ObjCliente cliente = gson.fromJson(data, ObjCliente.class);
                                                        if (cliente == null) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(Vender.this, "El cliente no existe", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            return;
                                                        }
                                                        String uri = Uri.parse(d.getUrl() + "/producto/buscarProducto").buildUpon()
                                                                .appendQueryParameter("id", codPro)
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
                                                                        Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                            }

                                                            @Override
                                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                                if(response.isSuccessful()){
                                                                    String data=response.body().string();

                                                                    Gson gson = new GsonBuilder()
                                                                            .setDateFormat("yyyy-MM-dd")
                                                                            .create();
                                                                    ObjProducto producto=gson.fromJson(data, ObjProducto.class);
                                                                    if(producto==null){
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(Vender.this, "El producto no existe", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });

                                                                        return;
                                                                    }
                                                                    VenderPK vpk = new VenderPK(txtCodProVend.getText().toString(), txtCodCliVend.getText().toString());

                                                                    ObjVender v = new ObjVender(vpk, Date.valueOf(txtFechaVend.getText().toString()),cliente, empleado,producto);

                                                                    insertarVend(client, v);

                                                                }else{
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(Vender.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        });


                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(Vender.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                }
                                            });



                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Vender.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
                }
            }
        });

        modVend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarVend(txtCodCliVend,txtCodProVend,txtCodEmpVend,txtFechaVend);

                if (correcto == true) {
                    String codCli = txtCodCliVend.getText().toString();
                    String codPro = txtCodProVend.getText().toString();
                    String codEmp=txtCodEmpVend.getText().toString();
                    String uri = Uri.parse(d.getUrl() + "/vender/buscarVenta").buildUpon()
                            .appendQueryParameter("id", codPro)
                            .appendQueryParameter("id2", codCli)
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
                                    Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();

                                Gson gson = new GsonBuilder()
                                        .setDateFormat("yyyy-MM-dd")
                                        .create();
                                ObjVender vender=gson.fromJson(data, ObjVender.class);
                                if(vender==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Vender.this, "La venta no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/empleado/buscarEmpleado").buildUpon()
                                        .appendQueryParameter("id", codEmp)
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
                                                Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();

                                            Gson gson=new Gson();
                                            ObjEmpleado empleado=gson.fromJson(data, ObjEmpleado.class);
                                            if(empleado==null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Vender.this, "El empleado no existe", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                return;
                                            }
                                            String uri = Uri.parse(d.getUrl() + "/cliente/buscarCliente").buildUpon()
                                                    .appendQueryParameter("id", codCli)
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
                                                            Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                    if (response.isSuccessful()) {
                                                        String data = response.body().string();

                                                        Gson gson = new Gson();
                                                        ObjCliente cliente = gson.fromJson(data, ObjCliente.class);
                                                        if (cliente == null) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(Vender.this, "El cliente no existe", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            return;
                                                        }
                                                        String uri = Uri.parse(d.getUrl() + "/producto/buscarProducto").buildUpon()
                                                                .appendQueryParameter("id", codPro)
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
                                                                        Toast.makeText(Vender.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                            }

                                                            @Override
                                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                                if(response.isSuccessful()){
                                                                    String data=response.body().string();

                                                                    Gson gson = new GsonBuilder()
                                                                            .setDateFormat("yyyy-MM-dd")
                                                                            .create();
                                                                    ObjProducto producto=gson.fromJson(data, ObjProducto.class);
                                                                    if(producto==null){
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(Vender.this, "El producto no existe", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });

                                                                        return;
                                                                    }
                                                                    VenderPK vpk = new VenderPK(txtCodProVend.getText().toString(), txtCodCliVend.getText().toString());

                                                                    ObjVender v = new ObjVender(vpk, Date.valueOf(txtFechaVend.getText().toString()),cliente, empleado,producto);

                                                                    modificarVend(client, v);

                                                                }else{
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(Vender.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        });


                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(Vender.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                }
                                            });



                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Vender.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });

    }


    public void insertarVend( OkHttpClient client,ObjVender vender){
        datos d=getDatos();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        String json = gson.toJson(vender);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/vender/insertar").buildUpon()
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
                        Toast.makeText(Vender.this, "No insertado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Vender.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Vender.this, "No insertado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modificarVend( OkHttpClient client,ObjVender vender){
        datos d=getDatos();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        String json = gson.toJson(vender);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + "/vender/modificar").buildUpon()
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
                        Toast.makeText(Vender.this, "No modificado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Vender.this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Vender.this, "No modificado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }



    private boolean validarVend(EditText txtCodCliVend, EditText txtCodProVend,EditText txtCodEmpVend,EditText txtFechaVend){
        if (txtCodProVend.getText().toString().isEmpty() == true||txtCodProVend.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el código de producto", Toast.LENGTH_SHORT).show();
            txtCodProVend.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^p\\d{2}$");
            boolean correcto = p.matcher(txtCodProVend.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de código de producto incorrecto(Formato válido:p+dos dígitos)", Toast.LENGTH_SHORT).show();
                txtCodProVend.requestFocus();
                txtCodProVend.setText(" ");
                return false;
            }
        }

        if (txtCodEmpVend.getText().toString().isEmpty() == true||txtCodEmpVend.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir el código de empleado", Toast.LENGTH_SHORT).show();
            txtCodEmpVend.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^e\\d{2}$");
            boolean correcto = p.matcher(txtCodEmpVend.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de código de empleado incorrecto(Formato válido:e+dos dígitos)", Toast.LENGTH_SHORT).show();
                txtCodEmpVend.requestFocus();
                txtCodEmpVend.setText(" ");
                return false;
            }
        }
        if (txtCodCliVend.getText().toString().trim().isEmpty() == true||txtCodCliVend.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el código de cliente", Toast.LENGTH_SHORT).show();
            txtCodCliVend.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^c\\d{3}$");
            boolean correcto = p.matcher(txtCodCliVend.getText().toString().trim()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de código de cliente incorrecto(Formato válido:c+tres dígitos)", Toast.LENGTH_SHORT).show();
                txtCodCliVend.requestFocus();
                txtCodCliVend.setText(" ");
                return false;
            }
        }
        if (txtFechaVend.getText().toString().isEmpty() == true||txtFechaVend.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir la fecha de devolución", Toast.LENGTH_SHORT).show();

            txtFechaVend.requestFocus();
            return false;
        } else {
            try {
                Date fecha = Date.valueOf(txtFechaVend.getText().toString());

            } catch (IllegalArgumentException iae) {
                Toast.makeText(this, "Formato de fecha incorrecto(Formato correcto:yyyy-mm-dd)", Toast.LENGTH_SHORT).show();
                txtFechaVend.requestFocus();
                txtFechaVend.setText(" ");
                return false;
            }
        }
       return true;
    }
    private datos getDatos(){
        SharedPreferences preferencias= getBaseContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        preferenceHelper helper=new preferenceHelper(preferencias);
        datos d=helper.cargar();
        return d;
    }

    private  int getTamaño(){
        PreferenciasHelper helper=new PreferenciasHelper(getBaseContext());
        SQLiteDatabase bd=helper.getWritableDatabase();
        AjustesDAO ajustesDAO=new AjustesDAO();
        Ajustes a=ajustesDAO.obtenerAjusteSeleccionado(bd);
        return  a.getTamaño();
    }
    private  void cambiarTamaño(){
        int tamaño=getTamaño();



        EditText txtCodCliVend=findViewById(R.id.txtCodCliVend);
        EditText txtCodProVend=findViewById(R.id.txtCodProVend);
        EditText txtCodEmpVend=findViewById(R.id.txtCodEmpVend);
        EditText txtFechaVend=findViewById(R.id.txtFechaVend);


        Button insVend=findViewById(R.id.btnInsVend);
        Button modVend=findViewById(R.id.btnModVend);
        Button verVend=findViewById(R.id.btnVisualizarVend);
        Button salirVend=findViewById(R.id.btnSalirVend);
        TextView texto1=findViewById(R.id.textViewVend);
        TextView texto2=findViewById(R.id.textView3Vend);
        TextView texto3=findViewById(R.id.textView4Vend);
        TextView texto4=findViewById(R.id.textView7Vend);

        txtCodCliVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtCodProVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtCodEmpVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtFechaVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        insVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto4.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        modVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        verVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirVend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }
}