package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.Objetos.ObjVender;
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Cliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cambiarTamaño();
        String usuario = null;
        String rol = null;
        Intent intent = getIntent();
        EditText txtCodCli=findViewById(R.id.txtCodCli);
        EditText txtDniCli=findViewById(R.id.txtDniCli);
        EditText txtNombreCli=findViewById(R.id.txtNombreCli);
        EditText txtCuentaCli=findViewById(R.id.txtCuentaCli);
        EditText txtTlfCli=findViewById(R.id.txtTlfCli);


        OkHttpClient client=new OkHttpClient();
        Button insCli=findViewById(R.id.btnInsCli);
        Button modCli=findViewById(R.id.btnModCli);
        Button borrarCli=findViewById(R.id.btnBorrarCli);
        Button verCli=findViewById(R.id.btnVisualizarCli);
        Button salirCli=findViewById(R.id.btnSalirCli);
        ImageButton ajustes=findViewById(R.id.btnImagenCli);
        try {
            InputStream stream=getAssets().open("ajustes.png");


            byte[] imageBytes = new byte[stream.available()];
            DataInputStream dataInputStream = new DataInputStream(stream);
            dataInputStream.readFully(imageBytes);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ajustes.setImageBitmap(bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        txtCodCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    if (txtCodCli.getText().toString().trim().isEmpty() == true || txtCodCli.getText().toString().trim() == null) {
                        return;
                    }
                    datos d=getDatos();
                    String codCli = txtCodCli.getText().toString().trim();
                    String uri = Uri.parse(d.getUrl() + ":8080/cliente/buscarCliente").buildUpon()
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
                                    Toast.makeText(Cliente.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();

                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String data = response.body().string();

                                Gson gson = new Gson();
                                ObjCliente cliente = gson.fromJson(data, ObjCliente.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(cliente!=null){
                                            txtDniCli.setText(cliente.getDni());
                                            txtCuentaCli.setText(cliente.getNumCuenta()+"");
                                            txtNombreCli.setText(cliente.getNombre());
                                            txtTlfCli.setText(cliente.getTelefono()+"");

                                        }else{
                                            txtDniCli.setText("");
                                            txtCuentaCli.setText("");
                                            txtNombreCli.setText("");
                                            txtTlfCli.setText("");
                                        }

                                    }
                                });

                            }
                            response.close();
                        }
                    });
                }
            }
        });





        if (intent != null) {
            usuario = intent.getStringExtra("usuario");
            rol = intent.getStringExtra("rol");
            if(rol.equals("Inversionista")) {
                borrarCli.setActivated(false);
            }else{
                borrarCli.setActivated(true);
            }

        }
        String usuario2=usuario;
        String rol2=rol;

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes=new Intent(getBaseContext(), Config.class);
                ajustes.putExtra("usuario",usuario2);
                ajustes.putExtra("rol",rol2);
                ajustes.putExtra("clase","cliente");
                startActivity(ajustes);
                finish();
            }
        });
        salirCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir=new Intent(getBaseContext(), Gestion.class);
                salir.putExtra("usuario",usuario2);
                salir.putExtra("rol",rol2);
                startActivity(salir);
                finish();
            }
        });

        verCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","cliente");
                gestion.putExtra("usuario",usuario2);
                gestion.putExtra("rol",rol2);
                startActivity(gestion);
                finish();
            }
        });
        insCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarCli(txtCodCli,txtDniCli,txtNombreCli,txtCuentaCli,txtTlfCli);

                if (correcto == true) {
                    String codCli = txtCodCli.getText().toString().trim();
                    String uri = Uri.parse(d.getUrl() + ":8080/cliente/buscarCliente").buildUpon()
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
                                    Toast.makeText(Cliente.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();

                                Gson gson=new Gson();
                                ObjCliente cliente=gson.fromJson(data, ObjCliente.class);
                                if(cliente!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Cliente.this, R.string.cliExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                cliente=new ObjCliente(txtCodCli.getText().toString().trim(),txtDniCli.getText().toString().trim(),Integer.parseInt(txtTlfCli.getText().toString().trim()),txtNombreCli.getText().toString().trim(),Long.parseLong(txtCuentaCli.getText().toString().trim()));

                                insertarCliente( client,cliente);


                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Cliente.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            response.close();
                        }
                    });

                }
            }
        });
        modCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarCli(txtCodCli,txtDniCli,txtNombreCli,txtCuentaCli,txtTlfCli);

                if (correcto == true) {
                    String codCli = txtCodCli.getText().toString().trim();
                    String uri = Uri.parse(d.getUrl() + ":8080/cliente/buscarCliente").buildUpon()
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
                                    Toast.makeText(Cliente.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();

                                Gson gson=new Gson();
                                ObjCliente cliente=gson.fromJson(data, ObjCliente.class);
                                if(cliente==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Cliente.this, R.string.cliNoExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                cliente=new ObjCliente(txtCodCli.getText().toString().trim(),txtDniCli.getText().toString().trim(),Integer.parseInt(txtTlfCli.getText().toString().trim()),txtNombreCli.getText().toString().trim(),Long.parseLong(txtCuentaCli.getText().toString().trim()));

                                modificarCliente( client,cliente);

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Cliente.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            response.close();
                        }
                    });

                }
            }
        });
        borrarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rol2.equals("Inversionista")) {
                    Toast.makeText(Cliente.this, R.string.accCli, Toast.LENGTH_SHORT).show();
                } else {
                    datos d = getDatos();
                    boolean correcto = validarCli(txtCodCli, txtDniCli, txtNombreCli, txtCuentaCli, txtTlfCli);

                    if (correcto == true) {
                        String codCli = txtCodCli.getText().toString().trim();
                        String uri = Uri.parse(d.getUrl() + ":8080/cliente/buscarCliente").buildUpon()
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
                                        Toast.makeText(Cliente.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                        call.cancel();
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
                                                Toast.makeText(Cliente.this, R.string.cliNoExiste, Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        return;
                                    }
                                    String uri = Uri.parse(d.getUrl() + ":8080/vender/buscarClienteEnVenta").buildUpon()
                                            .appendQueryParameter("id", cliente.getIdCliente())
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
                                                    Toast.makeText(Cliente.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                                    call.cancel();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                            if (response.isSuccessful()) {
                                                String data = response.body().string();

                                                Gson gson = new GsonBuilder()
                                                        .setDateFormat("yyyy-MM-dd")
                                                        .create();
                                                ObjVender vender = gson.fromJson(data, ObjVender.class);
                                                if (vender != null) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(Cliente.this, R.string.cliEnVenta, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    return;
                                                }

                                                borrarCliente(client, cliente);


                                            } else {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Cliente.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                            response.close();
                                        }
                                    });


                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Cliente.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                response.close();
                            }
                        });

                    }
                }
            }
        });

    }

    public void limpiarDatos(){
        EditText txtCodCli=findViewById(R.id.txtCodCli);
        EditText txtDniCli=findViewById(R.id.txtDniCli);
        EditText txtNombreCli=findViewById(R.id.txtNombreCli);
        EditText txtCuentaCli=findViewById(R.id.txtCuentaCli);
        EditText txtTlfCli=findViewById(R.id.txtTlfCli);
        txtCodCli.setText("");
        txtDniCli.setText("");
        txtCuentaCli.setText("");
        txtNombreCli.setText("");
        txtTlfCli.setText("");
    }
    public void insertarCliente( OkHttpClient client,ObjCliente cliente){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(cliente);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + ":8080/cliente/insertar").buildUpon()
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
                        Toast.makeText(Cliente.this, R.string.NoInsPet, Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, R.string.ins, Toast.LENGTH_SHORT).show();
                        }
                    });
                    limpiarDatos();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, R.string.NoInsResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                response.close();
            }
        });
    }
    public void modificarCliente( OkHttpClient client,ObjCliente cliente){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(cliente);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + ":8080/cliente/modificar").buildUpon()
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
                        Toast.makeText(Cliente.this, R.string.NoModPet, Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, R.string.mod, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, R.string.NoModResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                response.close();
            }
        });
    }
    public void borrarCliente( OkHttpClient client,ObjCliente cliente){
        datos d=getDatos();
        Gson gson = new Gson();

        String json = gson.toJson(cliente);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + ":8080/cliente/borrar").buildUpon()
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
                        Toast.makeText(Cliente.this, R.string.NoBorrarPet, Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, R.string.borrar, Toast.LENGTH_SHORT).show();
                        }
                    });
                    limpiarDatos();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, R.string.NoBorrarResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                response.close();
            }
        });
    }


    public boolean validarCli(EditText txtCodCli,EditText txtDniCli,EditText txtNombreCli, EditText txtCuentaCli,EditText txtTlfCli){
        if (txtCodCli.getText().toString().trim().isEmpty() == true||txtCodCli.getText().toString()==null) {

            Toast.makeText(this, R.string.intCodigo, Toast.LENGTH_SHORT).show();
            txtCodCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^c\\d{3}$");
            boolean correcto = p.matcher(txtCodCli.getText().toString().trim()).matches();
            if (correcto == false) {
                Toast.makeText(this, R.string.intCodigoCli, Toast.LENGTH_SHORT).show();
                txtCodCli.requestFocus();
                txtCodCli.setText("");
                return false;
            }
        }
        if (txtDniCli.getText().toString().trim().isEmpty() == true||txtDniCli.getText().toString()==null) {

            Toast.makeText(this, R.string.intDni, Toast.LENGTH_SHORT).show();
            txtDniCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{8}[A-HJ-NP-TV-Z]$");
            boolean correcto = p.matcher(txtDniCli.getText()).matches();
            if (correcto == false) {

                Toast.makeText(this, R.string.intDniForm, Toast.LENGTH_SHORT).show();
                txtDniCli.requestFocus();
                txtDniCli.setText("");
                return false;
            }
        }
        if (txtNombreCli.getText().toString().trim().isEmpty() == true||txtNombreCli.getText().toString()==null) {
            Toast.makeText(this, R.string.intNombre, Toast.LENGTH_SHORT).show();
            txtNombreCli.requestFocus();
            return false;
        } else {
            for (int i = 0; i < txtNombreCli.getText().length()-1; i++) {
                if (Character.isDigit(txtNombreCli.getText().toString().trim().charAt(i))) {

                    Toast.makeText(this, R.string.intNombreNum, Toast.LENGTH_SHORT).show();
                    txtNombreCli.requestFocus();
                    txtNombreCli.setText("");
                    return false;
                }
            }
        }

        if (txtTlfCli.getText().toString().trim().isEmpty() == true||txtTlfCli.getText().toString()==null) {
            Toast.makeText(this, R.string.intTlf, Toast.LENGTH_SHORT).show();
            txtTlfCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{9}");
            boolean correcto = p.matcher(txtTlfCli.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this,  R.string.intTlfForm, Toast.LENGTH_SHORT).show();
                txtTlfCli.requestFocus();
                txtTlfCli.setText("");
                return false;
            }
            try{
                int i=Integer.parseInt(txtTlfCli.getText().toString().trim());
            }catch (NumberFormatException nfe){
                Toast.makeText(this,  R.string.intTlfForm, Toast.LENGTH_SHORT).show();
                txtTlfCli.requestFocus();
                txtTlfCli.setText("");
                return false;
            }
        }

        if (txtCuentaCli.getText().toString().trim().isEmpty() == true||txtTlfCli.getText().toString()==null) {

            Toast.makeText(this,  R.string.intCuenta, Toast.LENGTH_SHORT).show();
            txtCuentaCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{10}");
            boolean correcto = p.matcher(txtCuentaCli.getText().toString().trim()).matches();
            if (correcto == false) {
                Toast.makeText(this,  R.string.intCuentaForm, Toast.LENGTH_SHORT).show();

                txtCuentaCli.requestFocus();
                txtCuentaCli.setText("");
                return false;
            }
            try{
                long i=Long.parseLong(txtCuentaCli.getText().toString().trim());
            }catch (NumberFormatException nfe){
                Toast.makeText(this,  R.string.intCuentaForm, Toast.LENGTH_SHORT).show();
                txtCuentaCli.requestFocus();
                txtCuentaCli.setText("");
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
        EditText txtCodCli=findViewById(R.id.txtCodCli);
        EditText txtDniCli=findViewById(R.id.txtDniCli);
        EditText txtNombreCli=findViewById(R.id.txtNombreCli);
        EditText txtCuentaCli=findViewById(R.id.txtCuentaCli);
        EditText txtTlfCli=findViewById(R.id.txtTlfCli);
        TextView texto1=findViewById(R.id.textViewCli);
        TextView texto2=findViewById(R.id.textView2Cli);
        TextView texto3=findViewById(R.id.textView4Cli);
        TextView texto4=findViewById(R.id.textView5Cli);
        TextView texto5=findViewById(R.id.textView6Cli);

        Button insCli=findViewById(R.id.btnInsCli);
        Button modCli=findViewById(R.id.btnModCli);
        Button borrarCli=findViewById(R.id.btnBorrarCli);
        Button verCli=findViewById(R.id.btnVisualizarCli);
        Button salirCli=findViewById(R.id.btnSalirCli);


        txtCodCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtDniCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtNombreCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtCuentaCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtTlfCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto4.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto5.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        insCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        modCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        borrarCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        verCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }
}