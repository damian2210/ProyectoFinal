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
import android.widget.Spinner;
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
import com.example.myapplication.Objetos.ObjVender;
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Empleado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_empleado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cambiarTamaño();
        String usuario = null;
        String rol = null;
        Intent intent = getIntent();
        EditText txtCodEmp=findViewById(R.id.txtCodEmp);
        EditText txtDniEmp=findViewById(R.id.txtDniEmp);
        EditText txtTlfEmp=findViewById(R.id.txtTlfEmp);
        EditText txtContraEmp=findViewById(R.id.txtContraEmp);
        EditText txtUsuarioEmp=findViewById(R.id.txtUsuarioEmp);
        Spinner spin=findViewById(R.id.spnRolEmp);


        OkHttpClient client=new OkHttpClient();
        Button insEmp=findViewById(R.id.btnInsEmp);
        Button modEmp=findViewById(R.id.btnModEmp);
        Button borrarEmp=findViewById(R.id.btnBorrarEmp);
        Button verEmp=findViewById(R.id.btnVisualizarEmp);
        Button salirEmp=findViewById(R.id.btnSalirEmp);
        ImageButton ajustes=findViewById(R.id.btnImagenEmp);



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
        salirEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir=new Intent(getBaseContext(), Gestion.class);
                salir.putExtra("usuario",usuario2);
                salir.putExtra("rol",rol2);
                startActivity(salir);
                finish();
            }
        });


        verEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","empleado");

                startActivity(gestion);
                finish();
            }
        });
        insEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarEmp(txtCodEmp,txtDniEmp,txtTlfEmp,txtContraEmp,txtUsuarioEmp);

                if (correcto == true) {
                    String codEmp = txtCodEmp.getText().toString();
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
                                    Toast.makeText(Empleado.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();

                                Gson gson=new Gson();
                                ObjEmpleado empleado=gson.fromJson(data, ObjEmpleado.class);
                                if(empleado!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Empleado.this, R.string.empExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/empleado/comprobarUsuario").buildUpon()
                                        .appendQueryParameter("usuario",txtUsuarioEmp.getText().toString())
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
                                                Toast.makeText(Empleado.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();
                                            Gson gson=new Gson();
                                            ObjEmpleado e =gson.fromJson(data, ObjEmpleado.class);
                                            if(e!=null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Empleado.this, R.string.usuExiste, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }

                                            e=new ObjEmpleado(txtCodEmp.getText().toString(),txtDniEmp.getText().toString(),Integer.parseInt(txtTlfEmp.getText().toString()),spin.getSelectedItem().toString(),txtUsuarioEmp.getText().toString(),txtContraEmp.getText().toString());

                                            insertarEmp( client,e);

                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Empleado.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });



                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Empleado.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        modEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarEmp(txtCodEmp,txtDniEmp,txtTlfEmp,txtContraEmp,txtUsuarioEmp);

                if (correcto == true) {
                    String codEmp = txtCodEmp.getText().toString();
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
                                    Toast.makeText(Empleado.this, R.string.peticion, Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(Empleado.this, R.string.empNoExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/empleado/comprobarUsuario").buildUpon()
                                        .appendQueryParameter("usuario",txtUsuarioEmp.getText().toString())
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
                                                Toast.makeText(Empleado.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if(response.isSuccessful()){
                                            String data=response.body().string();
                                            Gson gson=new Gson();
                                            ObjEmpleado e =gson.fromJson(data, ObjEmpleado.class);
                                            if(e!=null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Empleado.this, R.string.usuExiste, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }

                                            e=new ObjEmpleado(txtCodEmp.getText().toString(),txtDniEmp.getText().toString(),Integer.parseInt(txtTlfEmp.getText().toString()),spin.getSelectedItem().toString(),txtUsuarioEmp.getText().toString(),txtContraEmp.getText().toString());

                                            modificarEmp(client,e);

                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Empleado.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });



                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Empleado.this,  R.string.respuesta, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        borrarEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarEmp(txtCodEmp,txtDniEmp,txtTlfEmp,txtContraEmp,txtUsuarioEmp);

                if (correcto == true) {
                    String codEmp = txtCodEmp.getText().toString();
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
                                    Toast.makeText(Empleado.this, R.string.peticion, Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(Empleado.this, R.string.empNoExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }


                                String uri = Uri.parse(d.getUrl() + "/vender/buscarEmpEnVenta").buildUpon()
                                        .appendQueryParameter("id",codEmp)
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
                                                Toast.makeText(Empleado.this, R.string.peticion, Toast.LENGTH_SHORT).show();
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
                                            ObjVender v =gson.fromJson(data, ObjVender.class);
                                            if(v!=null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Empleado.this, R.string.empEnVenta, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }


                                            borrarEmp( client,empleado);

                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Empleado.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });



                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Empleado.this,  R.string.respuesta, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });

    }

    public void insertarEmp( OkHttpClient client,ObjEmpleado empleado){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(empleado);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/empleado/insertar").buildUpon()
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
                        Toast.makeText(Empleado.this, R.string.NoInsPet, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Empleado.this,  R.string.ins, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Empleado.this,  R.string.NoInsResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modificarEmp( OkHttpClient client,ObjEmpleado empleado){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(empleado);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + "/empleado/modificar").buildUpon()
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
                        Toast.makeText(Empleado.this,  R.string.NoModPet, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Empleado.this, R.string.mod, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Empleado.this, R.string.NoModResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void borrarEmp( OkHttpClient client,ObjEmpleado empleado){
        datos d=getDatos();
        Gson gson = new Gson();

        String json = gson.toJson(empleado);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/empleado/borrar").buildUpon()
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
                        Toast.makeText(Empleado.this, R.string.NoBorrarPet, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Empleado.this, R.string.borrar, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Empleado.this, R.string.NoBorrarResp, Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
    }

    public boolean validarEmp(EditText txtCodEmp,EditText txtDniEmp,EditText txtTlfEmp, EditText txtContraEmp,EditText txtUsuarioEmp) {
        if (txtCodEmp.getText().toString().isEmpty() == true||txtCodEmp.getText().toString()==null) {
            Toast.makeText(this, R.string.intCodigo, Toast.LENGTH_SHORT).show();
            txtCodEmp.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^e\\d{2}$");
            boolean correcto = p.matcher(txtCodEmp.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, R.string.intCodigoEmp, Toast.LENGTH_SHORT).show();
                txtCodEmp.requestFocus();
                txtCodEmp.setText(" ");
                return false;
            }
        }
        if (txtContraEmp.getText().toString().isEmpty() == true||txtContraEmp.getText().toString()==null) {
            Toast.makeText(this, R.string.intContra, Toast.LENGTH_SHORT).show();
            txtContraEmp.requestFocus();
            return false;
        }

        if (txtUsuarioEmp.getText().toString().isEmpty() == true||txtUsuarioEmp.getText().toString()==null) {
            Toast.makeText(this, R.string.intUser, Toast.LENGTH_SHORT).show();
            txtUsuarioEmp.requestFocus();
            return false;
        }
        if (txtTlfEmp.getText().toString().trim().isEmpty() == true||txtTlfEmp.getText().toString()==null) {
            Toast.makeText(this, R.string.intTlf, Toast.LENGTH_SHORT).show();
            txtTlfEmp.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{9}");
            boolean correcto = p.matcher(txtTlfEmp.getText()).matches();
            if (correcto == false) {

                Toast.makeText(this, R.string.intTlfForm, Toast.LENGTH_SHORT).show();
                txtTlfEmp.requestFocus();
                txtTlfEmp.setText(" ");
                return false;
            }
        }
        if (txtDniEmp.getText().toString().isEmpty() == true||txtDniEmp.getText().toString()==null) {

            Toast.makeText(this, R.string.intDni, Toast.LENGTH_SHORT).show();
            txtDniEmp.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{8}[A-HJ-NP-TV-Z]$");
            boolean correcto = p.matcher(txtDniEmp.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, R.string.intDniForm, Toast.LENGTH_SHORT).show();
                txtDniEmp.requestFocus();
                txtDniEmp.setText(" ");
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

        EditText txtCodEmp=findViewById(R.id.txtCodEmp);
        EditText txtDniEmp=findViewById(R.id.txtDniEmp);
        EditText txtTlfEmp=findViewById(R.id.txtTlfEmp);
        EditText txtContraEmp=findViewById(R.id.txtContraEmp);
        EditText txtUsuarioEmp=findViewById(R.id.txtUsuarioEmp);

        TextView texto1=findViewById(R.id.textViewEmp);
        TextView texto2=findViewById(R.id.textView3Emp);
        TextView texto3=findViewById(R.id.textView4Emp);
        TextView texto4=findViewById(R.id.textView5Emp);
        TextView texto5=findViewById(R.id.textView6Emp);
        TextView texto6=findViewById(R.id.textView7Emp);


        Button insEmp=findViewById(R.id.btnInsEmp);
        Button modEmp=findViewById(R.id.btnModEmp);
        Button borrarEmp=findViewById(R.id.btnBorrarEmp);
        Button verEmp=findViewById(R.id.btnVisualizarEmp);
        Button salirEmp=findViewById(R.id.btnSalirEmp);


        txtCodEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtDniEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtTlfEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtContraEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtUsuarioEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto4.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto5.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto6.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        insEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        modEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        borrarEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        verEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirEmp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }

}