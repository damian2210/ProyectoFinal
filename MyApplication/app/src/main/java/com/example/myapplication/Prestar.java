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
import com.example.myapplication.Objetos.PrestarPK;
import com.example.myapplication.Objetos.VenderPK;
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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

        txtCodSucRPrest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    if (txtCodSucRPrest.getText().toString().trim().isEmpty() == true || txtCodSucRPrest.getText().toString().trim() == null||txtCodSucPPrest.getText().toString().trim().isEmpty() == true || txtCodSucPPrest.getText().toString().trim() == null) {
                        return;
                    }
                    datos d=getDatos();
                    String sucR = txtCodSucRPrest.getText().toString().trim();
                    String sucP = txtCodSucPPrest.getText().toString().trim();
                    String uri = Uri.parse(d.getUrl() + ":8080/prestar/buscarPrestamo").buildUpon()
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
                                    Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();

                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String data = response.body().string();

                                Gson gson = new Gson();
                                ObjPrestar prestar = gson.fromJson(data, ObjPrestar.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(prestar!=null){
                                            txtCantPrest.setText(prestar.getCantidad()+"");


                                        }else{
                                          txtCantPrest.setText("");
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
        txtCodSucPPrest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    if (txtCodSucRPrest.getText().toString().trim().isEmpty() == true || txtCodSucRPrest.getText().toString().trim() == null||txtCodSucPPrest.getText().toString().trim().isEmpty() == true || txtCodSucPPrest.getText().toString().trim() == null) {
                        return;
                    }
                    datos d=getDatos();
                    String sucR = txtCodSucRPrest.getText().toString().trim();
                    String sucP = txtCodSucPPrest.getText().toString().trim();
                    String uri = Uri.parse(d.getUrl() + ":8080/prestar/buscarPrestamo").buildUpon()
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
                                    Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();

                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String data = response.body().string();

                                Gson gson = new Gson();
                                ObjPrestar prestar = gson.fromJson(data, ObjPrestar.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(prestar!=null){
                                            txtCantPrest.setText(prestar.getCantidad()+"");


                                        }else{
                                            txtCantPrest.setText("");
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


        }
        String usuario2=usuario;
        String rol2=rol;
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes=new Intent(getBaseContext(), Config.class);
                ajustes.putExtra("usuario",usuario2);
                ajustes.putExtra("rol",rol2);
                ajustes.putExtra("clase","prestar");
                startActivity(ajustes);
                finish();
            }
        });
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
                gestion.putExtra("usuario",usuario2);
                gestion.putExtra("rol",rol2);
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
                    String sucR = txtCodSucRPrest.getText().toString().trim();
                    String sucP = txtCodSucPPrest.getText().toString().trim();

                    String uri = Uri.parse(d.getUrl() + ":8080/prestar/buscarPrestamo").buildUpon()
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
                                    Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();
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
                                            Toast.makeText(Prestar.this, R.string.prestExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + ":8080/sucursal/buscarSucursal").buildUpon()
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
                                                Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                                call.cancel();
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
                                                        Toast.makeText(Prestar.this, R.string.sucRecNoExiste, Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                return;
                                            }
                                            String uri = Uri.parse(d.getUrl() + ":8080/sucursal/buscarSucursal").buildUpon()
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
                                                            Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                                            call.cancel();
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
                                                                    Toast.makeText(Prestar.this, R.string.sucReaExiste, Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            return;
                                                        }

                                                        PrestarPK ppk = new PrestarPK(sucR,sucP);

                                                        ObjPrestar p = new ObjPrestar(ppk,Integer.parseInt(txtCantPrest.getText().toString().trim()),sucursalR,sucursalP);

                                                        insertarPrest(client, p);



                                                    } else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(Prestar.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                    response.close();
                                                }
                                            });
                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Prestar.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                        response.close();
                                    }
                                });

                            }
                            response.close();
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
                    String sucR = txtCodSucRPrest.getText().toString().trim();
                    String sucP = txtCodSucPPrest.getText().toString().trim();

                    String uri = Uri.parse(d.getUrl() + ":8080/prestar/buscarPrestamo").buildUpon()
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
                                    Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                    call.cancel();
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
                                            Toast.makeText(Prestar.this, R.string.prestNoExiste, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + ":8080/sucursal/buscarSucursal").buildUpon()
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
                                                Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                                call.cancel();
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
                                                        Toast.makeText(Prestar.this, R.string.sucRecNoExiste, Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                return;
                                            }
                                            String uri = Uri.parse(d.getUrl() + ":8080/sucursal/buscarSucursal").buildUpon()
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
                                                            Toast.makeText(Prestar.this, R.string.peticion, Toast.LENGTH_SHORT).show();
                                                            call.cancel();
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
                                                                    Toast.makeText(Prestar.this, R.string.sucReaExiste, Toast.LENGTH_SHORT).show();
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
                                                                Toast.makeText(Prestar.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                    response.close();
                                                }
                                            });
                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Prestar.this, R.string.respuesta, Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                        response.close();
                                    }
                                });

                            }
                            response.close();
                        }
                    });
                }
            }
        });

    }

    public void limpiarDatos(){
        EditText txtCodSucRPrest=findViewById(R.id.txtCodSucRPrest);
        EditText txtCodSucPPrest=findViewById(R.id.txtCodSucPPrest);
        EditText txtCantPrest=findViewById(R.id.txtCantPrest);
        txtCodSucRPrest.setText("");
        txtCodSucPPrest.setText("");
        txtCantPrest.setText("");
    }
    public void insertarPrest( OkHttpClient client,ObjPrestar prestar){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(prestar);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + ":8080/prestar/insertar").buildUpon()
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
                        Toast.makeText(Prestar.this, R.string.NoInsPet, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Prestar.this, R.string.ins, Toast.LENGTH_SHORT).show();
                        }
                    });
                    limpiarDatos();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prestar.this, R.string.NoInsResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                response.close();
            }
        });
    }
    public void modificarPrest( OkHttpClient client,ObjPrestar prestar){
        datos d=getDatos();
        Gson gson = new Gson();
        String json = gson.toJson(prestar);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + ":8080/prestar/modificar").buildUpon()
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
                        Toast.makeText(Prestar.this, R.string.NoModPet, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Prestar.this, R.string.mod, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Prestar.this, R.string.NoModResp, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                response.close();
            }
        });
    }



    private boolean validarPrest(EditText txtCodSucRPrest, EditText txtCodSucPPrest,EditText txtCantPrest){
        if (txtCodSucRPrest.getText().toString().trim().isEmpty() == true || txtCodSucRPrest.getText().toString()==null) {
            Toast.makeText(this, R.string.intCodigoSucR, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile("^s\\d{2}$");
            boolean correcto = p.matcher(txtCodSucRPrest.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this, R.string.intCodigoSuc, Toast.LENGTH_SHORT).show();
                txtCodSucRPrest.setText("");
                return false;
            }
        }
        if (txtCodSucPPrest.getText().toString().trim().isEmpty() == true || txtCodSucPPrest.getText().toString().trim()==null) {
            Toast.makeText(this,  R.string.intCodigoSucP, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile("^s\\d{2}$");
            boolean correcto = p.matcher(txtCodSucPPrest.getText().toString().trim()).matches();
            if (correcto == false) {

                Toast.makeText(this,  R.string.intCodigoSuc, Toast.LENGTH_SHORT).show();
                txtCodSucPPrest.setText("");
                return false;
            }
        }
        if (txtCantPrest.getText().toString().trim().isEmpty() == true||txtCantPrest.getText().toString().trim()==null) {

            Toast.makeText(this, R.string.intCant, Toast.LENGTH_SHORT).show();
            txtCantPrest.requestFocus();
            return false;
        } else {
            for (int i = 0; i < txtCantPrest.getText().length()-1; i++) {
                if (Character.isLetter(txtCantPrest.getText().toString().trim().charAt(i))) {
                    Toast.makeText(this, R.string.intCantForm, Toast.LENGTH_SHORT).show();
                    txtCantPrest.requestFocus();
                    txtCantPrest.setText("");
                    return false;
                }
            }
            try{
                int i=Integer.parseInt(txtCantPrest.getText().toString().trim());
            }catch (NumberFormatException nfe){
                Toast.makeText(this, R.string.intCantForm, Toast.LENGTH_SHORT).show();
                txtCantPrest.requestFocus();
                txtCantPrest.setText("");
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
        helper.close();
        bd.close();
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