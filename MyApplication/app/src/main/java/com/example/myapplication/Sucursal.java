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

import com.example.myapplication.Objetos.ObjEmpleado;
import com.example.myapplication.Objetos.ObjPrestar;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
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
        cambiarTamaño();
        String usuario = null;
        String rol = null;
        Intent intent = getIntent();
        EditText txtcodSuc=findViewById(R.id.txtCodSuc);
        EditText txtdireccionSuc=findViewById(R.id.txtDirSuc);
        EditText txttlfSuc=findViewById(R.id.txtTlfSuc);

        OkHttpClient client=new OkHttpClient();
        Button insSuc=findViewById(R.id.btnInsSuc);
        Button modSuc=findViewById(R.id.btnModSuc);
        Button borrarSuc=findViewById(R.id.btnBorrarSuc);
        Button verSuc=findViewById(R.id.btnVisualizarSuc);
        Button salirSuc=findViewById(R.id.btnSalirSuc);
        ImageButton ajustes=findViewById(R.id.btnImagenSuc);





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
        salirSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir=new Intent(getBaseContext(), Gestion.class);
                salir.putExtra("usuario",usuario2);
                salir.putExtra("rol",rol2);
                startActivity(salir);
                finish();
            }
        });


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
                datos d=getDatos();
                boolean correcto = validarSuc(txtcodSuc,txttlfSuc,txtdireccionSuc);

                if (correcto == true) {
                    String codSuc = txtcodSuc.getText().toString();
                    String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
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
                                    Toast.makeText(Sucursal.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();

                                Gson gson=new Gson();
                                ObjSucursal sucursal=gson.fromJson(data, ObjSucursal.class);
                                if(sucursal!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Sucursal.this, R.string.sucExiste, Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
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
                datos d=getDatos();
                boolean correcto = validarSuc(txtcodSuc,txttlfSuc,txtdireccionSuc);

                if (correcto == true) {
                    String codSuc = txtcodSuc.getText().toString();
                    String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
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
                                    Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
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
                                                    Toast.makeText(Sucursal.this, R.string.sucNoExiste, Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
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
                datos d=getDatos();
                boolean correcto = validarSuc(txtcodSuc,txttlfSuc,txtdireccionSuc);
                if (correcto == true) {
                    String codSuc = txtcodSuc.getText().toString();
                    String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
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
                                    Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(Sucursal.this, R.string.sucNoExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    return;
                                }




                                String uri = Uri.parse(d.getUrl() + "/prestar/buscarSucursalEnPrestamo").buildUpon()
                                        .appendQueryParameter("id",sucursal.getCodSucursal())
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
                                                Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();

                                            Gson gson=new Gson();
                                            ObjPrestar prestar =gson.fromJson(data, ObjPrestar.class);
                                            if(prestar!=null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Sucursal.this, R.string.sucEnPrest, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }

                                            borrarSucursal(client, sucursal);

                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });


                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Sucursal.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
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
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(sucursal);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/sucursal/insertar").buildUpon()
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
                        Toast.makeText(Sucursal.this, R.string.NoInsPet, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, R.string.ins, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, R.string.NoInsResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modSucursal( OkHttpClient client,ObjSucursal sucursal){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(sucursal);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + "/sucursal/modificar").buildUpon()
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
                        Toast.makeText(Sucursal.this, R.string.NoModPet, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, R.string.mod, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, R.string.NoModResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void borrarSucursal( OkHttpClient client,ObjSucursal sucursal){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(sucursal);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/sucursal/borrar").buildUpon()
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
                        Toast.makeText(Sucursal.this, R.string.NoBorrarPet, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, R.string.borrar, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Sucursal.this, R.string.NoBorrarResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }


    private boolean validarSuc(EditText txtcodSuc, EditText txttlfSuc,EditText txtDirSuc){

        if (txtcodSuc.getText().toString().trim().isEmpty() == true || txtcodSuc.getText().toString()==null) {
            Toast.makeText(this, R.string.intCodigo, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile("^s\\d{2}$");
            boolean correcto = p.matcher(txtcodSuc.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this, R.string.intCodigoSuc, Toast.LENGTH_SHORT).show();
                txtcodSuc.setText(" ");
                return false;
            }
        }

        if (txtDirSuc.getText().toString().trim().isEmpty() == true || txtDirSuc.getText().toString()==null) {
            Toast.makeText(this, R.string.intDir, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txttlfSuc.getText().toString().trim().isEmpty() == true||txttlfSuc.getText().toString()==null) {

            Toast.makeText(this, R.string.intTlf, Toast.LENGTH_SHORT).show();
            txttlfSuc.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{9}");
            boolean correcto = p.matcher(txttlfSuc.getText().toString()).matches();
            if (correcto == false) {
                Toast.makeText(this, R.string.intTlfForm, Toast.LENGTH_SHORT).show();
                txttlfSuc.requestFocus();
                txttlfSuc.setText(" ");
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
    private void cambiarTamaño(){
        int tamaño=getTamaño();

        EditText txtcodSuc=findViewById(R.id.txtCodSuc);
        EditText txtdireccionSuc=findViewById(R.id.txtDirSuc);
        EditText txttlfSuc=findViewById(R.id.txtTlfSuc);


        Button insSuc=findViewById(R.id.btnInsSuc);
        Button modSuc=findViewById(R.id.btnModSuc);
        Button borrarSuc=findViewById(R.id.btnBorrarSuc);
        Button verSuc=findViewById(R.id.btnVisualizarSuc);
        Button salirSuc=findViewById(R.id.btnSalirSuc);
        TextView texto1=findViewById(R.id.textViewSuc);
        TextView texto2=findViewById(R.id.textView3Suc);
        TextView texto3=findViewById(R.id.textView4Suc);

        txtcodSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtdireccionSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txttlfSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        insSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        modSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        borrarSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        verSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirSuc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);

    }


}