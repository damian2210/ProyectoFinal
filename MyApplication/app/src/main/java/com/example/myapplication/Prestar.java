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
import com.example.myapplication.Objetos.ObjVender;
import com.example.myapplication.Objetos.PrestarPK;
import com.example.myapplication.Objetos.VenderPK;
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
import com.google.gson.Gson;

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

public class Prestar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prestar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cambiarTamaño();
        String usuario = null;
        String rol = null;
        Intent intent = getIntent();

        EditText txtCodSucRPrest=findViewById(R.id.txtCodSucRPrest);
        EditText txtCodSucPPrest=findViewById(R.id.txtCodSucPPrest);
        EditText txtCantPrest=findViewById(R.id.txtCantPrest);


        OkHttpClient client=new OkHttpClient();
        Button insPrest=findViewById(R.id.btnInsPrest);
        Button modPrest=findViewById(R.id.btnModPrest);
        Button verPrest=findViewById(R.id.btnVisualizarPrest);
        Button salirPrest=findViewById(R.id.btnSalirPrest);



        ImageButton ajustes=findViewById(R.id.btnImagenPrest);
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
        salirPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir=new Intent(getBaseContext(), Gestion.class);
                salir.putExtra("usuario",usuario2);
                salir.putExtra("rol",rol2);
                startActivity(salir);
                finish();
            }
        });


        verPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","prestar");

                startActivity(gestion);
                finish();
            }
        });
        insPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarPrest(txtCodSucRPrest,txtCodSucPPrest,txtCantPrest);

                if (correcto == true) {
                    String sucR = txtCodSucRPrest.getText().toString();
                    String sucP = txtCodSucPPrest.getText().toString();

                    String uri = Uri.parse(d.getUrl() + "/prestar/buscarPrestamo").buildUpon()
                            .appendQueryParameter("id", sucR)
                            .appendQueryParameter("id2", sucP)
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
                                    Toast.makeText(Prestar.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Gson gson=new Gson();
                                ObjPrestar prestar=gson.fromJson(data, ObjPrestar.class);
                                if(prestar!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Prestar.this, "El préstamo ya existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
                                        .appendQueryParameter("id", sucR)
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
                                                Toast.makeText(Prestar.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();
                                            Gson gson=new Gson();
                                            ObjSucursal sucursalR=gson.fromJson(data, ObjSucursal.class);
                                            if(sucursalR==null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Prestar.this, "La sucursal que recibe el prestamo no existe", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                return;
                                            }
                                            String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
                                                    .appendQueryParameter("id", sucP)
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
                                                            Toast.makeText(Prestar.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                    if (response.isSuccessful()) {
                                                        String data = response.body().string();
                                                        Gson gson = new Gson();
                                                        ObjSucursal sucursalP = gson.fromJson(data, ObjSucursal.class);
                                                        if (sucursalP == null) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(Prestar.this, "La sucursal que realiza el prestamo no existe", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            return;
                                                        }

                                                        PrestarPK ppk = new PrestarPK(sucR,sucP);

                                                        ObjPrestar p = new ObjPrestar(ppk,Integer.parseInt(txtCantPrest.getText().toString()),sucursalR,sucursalP);

                                                        insertarPrest(client, p);


                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(Prestar.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                }
                                            });
                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Prestar.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
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

        modPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarPrest(txtCodSucRPrest,txtCodSucPPrest,txtCantPrest);

                if (correcto == true) {
                    String sucR = txtCodSucRPrest.getText().toString();
                    String sucP = txtCodSucPPrest.getText().toString();

                    String uri = Uri.parse(d.getUrl() + "/prestar/buscarPrestamo").buildUpon()
                            .appendQueryParameter("id", sucR)
                            .appendQueryParameter("id2", sucP)
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
                                    Toast.makeText(Prestar.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Gson gson=new Gson();
                                ObjPrestar prestar=gson.fromJson(data, ObjPrestar.class);
                                if(prestar==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Prestar.this, "El préstamo no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
                                        .appendQueryParameter("id", sucR)
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
                                                Toast.makeText(Prestar.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();
                                            Gson gson=new Gson();
                                            ObjSucursal sucursalR=gson.fromJson(data, ObjSucursal.class);
                                            if(sucursalR==null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Prestar.this, "La sucursal que recibe el prestamo no existe", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                return;
                                            }
                                            String uri = Uri.parse(d.getUrl() + "/sucursal/buscarSucursal").buildUpon()
                                                    .appendQueryParameter("id", sucP)
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
                                                            Toast.makeText(Prestar.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                    if (response.isSuccessful()) {
                                                        String data = response.body().string();
                                                        Gson gson = new Gson();
                                                        ObjSucursal sucursalP = gson.fromJson(data, ObjSucursal.class);
                                                        if (sucursalP == null) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(Prestar.this, "La sucursal que realiza el prestamo no existe", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            return;
                                                        }

                                                        PrestarPK ppk = new PrestarPK(sucR,sucP);

                                                        ObjPrestar p = new ObjPrestar(ppk,Integer.parseInt(txtCantPrest.getText().toString()),sucursalR,sucursalP);

                                                        modificarPrest(client, p);


                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(Prestar.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                }
                                            });
                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Prestar.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
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


    public void insertarPrest( OkHttpClient client,ObjPrestar prestar){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(prestar);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/prestar/insertar").buildUpon()
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
                        Toast.makeText(Prestar.this, "No insertado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prestar.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prestar.this, "No insertado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modificarPrest( OkHttpClient client,ObjPrestar prestar){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(prestar);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + "/prestar/modificar").buildUpon()
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
                        Toast.makeText(Prestar.this, "No modificado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prestar.this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prestar.this, "No modificado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }



    private boolean validarPrest(EditText txtCodSucRPrest, EditText txtCodSucPPrest,EditText txtCantPrest){
        if (txtCodSucRPrest.getText().toString().trim().isEmpty() == true || txtCodSucRPrest.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir el código de la sucursal que recibe el prestamo", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile("^s\\d{2}$");
            boolean correcto = p.matcher(txtCodSucRPrest.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this, "Formato de código de la sucursal que recibe el prestamo incorrecto(Formato válido:s+dos dígitos", Toast.LENGTH_SHORT).show();
                txtCodSucRPrest.setText(" ");
                return false;
            }
        }
        if (txtCodSucPPrest.getText().toString().trim().isEmpty() == true || txtCodSucPPrest.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir el código de la sucursal que realiza el prestamo", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile("^s\\d{2}$");
            boolean correcto = p.matcher(txtCodSucPPrest.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this, "Formato de código de la sucursal que realiza el prestamo incorrecto(Formato válido:s+dos dígitos", Toast.LENGTH_SHORT).show();
                txtCodSucPPrest.setText(" ");
                return false;
            }
        }
        if (txtCantPrest.getText().toString().isEmpty() == true||txtCantPrest.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir la cantidad", Toast.LENGTH_SHORT).show();
            txtCantPrest.requestFocus();
            return false;
        } else {
            for (int i = 0; i < txtCantPrest.getText().length(); i++) {
                if (Character.isLetter(txtCantPrest.getText().charAt(i))) {
                    Toast.makeText(this, "La cantidad solo pueden ser números", Toast.LENGTH_SHORT).show();
                    txtCantPrest.requestFocus();
                    txtCantPrest.setText(" ");
                    return false;
                }
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


        EditText txtCodSucRPrest=findViewById(R.id.txtCodSucRPrest);
        EditText txtCodSucPPrest=findViewById(R.id.txtCodSucPPrest);
        EditText txtCantPrest=findViewById(R.id.txtCantPrest);


        Button insPrest=findViewById(R.id.btnInsPrest);
        Button modPrest=findViewById(R.id.btnModPrest);
        Button verPrest=findViewById(R.id.btnVisualizarPrest);
        Button salirPrest=findViewById(R.id.btnSalirPrest);
        TextView texto1=findViewById(R.id.textViewPrest);
        TextView texto2=findViewById(R.id.textView3Prest);
        TextView texto3=findViewById(R.id.textView4Prest);

        txtCodSucRPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtCodSucPPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtCantPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        insPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        modPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        verPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirPrest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }
}