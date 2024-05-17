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

import com.example.myapplication.Objetos.ObjCliente;
import com.example.myapplication.Objetos.ObjPrestar;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.Objetos.ObjVender;
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


        verCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","sucursal");

                startActivity(gestion);
                finish();
            }
        });
        insCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = validarCli(txtCodCli,txtDniCli,txtNombreCli,txtCuentaCli,txtTlfCli);

                if (correcto == true) {
                    String codCli = txtCodCli.getText().toString();
                    String uri = Uri.parse("http://10.0.2.2:8080/cliente/buscarCli").buildUpon()
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
                                    Toast.makeText(Cliente.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Log.d("RESPUESTA",data.toString());
                                Gson gson=new Gson();
                                ObjCliente cliente=gson.fromJson(data, ObjCliente.class);
                                if(cliente!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Cliente.this, "El cliente ya existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                cliente=new ObjCliente(txtCodCli.getText().toString(),txtDniCli.getText().toString(),Integer.parseInt(txtTlfCli.getText().toString()),txtNombreCli.getText().toString(),txtCuentaCli.getText().toString());

                                insertarCliente( client,cliente);

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Cliente.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        modCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = validarCli(txtCodCli,txtDniCli,txtNombreCli,txtCuentaCli,txtTlfCli);

                if (correcto == true) {
                    String codCli = txtCodCli.getText().toString();
                    String uri = Uri.parse("http://10.0.2.2:8080/cliente/buscarCli").buildUpon()
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
                                    Toast.makeText(Cliente.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Log.d("RESPUESTA",data.toString());
                                Gson gson=new Gson();
                                ObjCliente cliente=gson.fromJson(data, ObjCliente.class);
                                if(cliente==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Cliente.this, "El cliente no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                cliente=new ObjCliente(txtCodCli.getText().toString(),txtDniCli.getText().toString(),Integer.parseInt(txtTlfCli.getText().toString()),txtNombreCli.getText().toString(),txtCuentaCli.getText().toString());

                                modificarCliente( client,cliente);

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Cliente.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        borrarCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = validarCli(txtCodCli,txtDniCli,txtNombreCli,txtCuentaCli,txtTlfCli);

                if (correcto == true) {
                    String codCli = txtCodCli.getText().toString();
                    String uri = Uri.parse("http://10.0.2.2:8080/cliente/buscarCli").buildUpon()
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
                                    Toast.makeText(Cliente.this, "Error en peticion", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String data=response.body().string();
                                Log.d("RESPUESTA",data.toString());
                                Gson gson=new Gson();
                                ObjCliente cliente=gson.fromJson(data, ObjCliente.class);
                                if(cliente==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Cliente.this, "El cliente no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse("http://10.0.2.2:8080/vender/buscarClienteEnVenta").buildUpon()
                                        .appendQueryParameter("id",cliente.getIdCliente())
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
                                            ObjVender vender =gson.fromJson(data, ObjVender.class);
                                            if(vender!=null){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Cliente.this, "El cliente participa en una venta,no se puede borrar", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }

                                            borrarCliente(client, cliente);

                                        }else{
                                            Log.d("Fallo","Error");
                                        }
                                    }
                                });


                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Cliente.this, "Error en respuesta", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                }
            }
        });

    }

    public void insertarCliente( OkHttpClient client,ObjCliente cliente){
        Gson gson = new Gson();
        String json = gson.toJson(cliente);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse("http://10.0.2.2:8080/cliente/insertar").buildUpon()
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
                        Toast.makeText(Cliente.this, "No insertado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, "No insertado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modificarCliente( OkHttpClient client,ObjCliente cliente){
        Gson gson = new Gson();
        String json = gson.toJson(cliente);
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse("http://10.0.2.2:8080/cliente/modificar").buildUpon()
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
                        Toast.makeText(Cliente.this, "No modificado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, "No modificado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void borrarCliente( OkHttpClient client,ObjCliente cliente){
        Gson gson = new Gson();

        String json = gson.toJson(cliente);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse("http://10.0.2.2:8080/cliente/borrar").buildUpon()
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
                        Toast.makeText(Cliente.this, "No borrado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Cliente.this, "No borrado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }


    public boolean validarCli(EditText txtCodCli,EditText txtDniCli,EditText txtNombreCli, EditText txtCuentaCli,EditText txtTlfCli){
        if (txtCodCli.getText().toString().trim().isEmpty() == true||txtCodCli.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el código", Toast.LENGTH_SHORT).show();
            txtCodCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^c\\d{3}$");
            boolean correcto = p.matcher(txtCodCli.getText().toString().trim()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de código incorrecto(Formato válido:c+tres dígitos)", Toast.LENGTH_SHORT).show();
                txtCodCli.requestFocus();
                txtCodCli.setText(" ");
                return false;
            }
        }
        if (txtNombreCli.getText().toString().trim().isEmpty() == true||txtNombreCli.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir el nombre", Toast.LENGTH_SHORT).show();
            txtNombreCli.requestFocus();
            return false;
        } else {
            for (int i = 0; i < txtNombreCli.getText().length(); i++) {
                if (Character.isDigit(txtNombreCli.getText().charAt(i))) {

                    Toast.makeText(this, "El nombre no puede tener números", Toast.LENGTH_SHORT).show();
                    txtNombreCli.requestFocus();
                    txtNombreCli.setText(" ");
                    return false;
                }
            }
        }
        if (txtDniCli.getText().toString().trim().isEmpty() == true||txtDniCli.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el DNI", Toast.LENGTH_SHORT).show();
            txtDniCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{8}[A-HJ-NP-TV-Z]$");
            boolean correcto = p.matcher(txtDniCli.getText()).matches();
            if (correcto == false) {

                Toast.makeText(this, "Formato de DNI incorrecto", Toast.LENGTH_SHORT).show();
                txtDniCli.requestFocus();
                txtDniCli.setText(" ");
                return false;
            }
        }
        if (txtTlfCli.getText().toString().trim().isEmpty() == true||txtTlfCli.getText().toString()==null) {
            Toast.makeText(this, "Tiene que introducir el teléfono", Toast.LENGTH_SHORT).show();
            txtTlfCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{9}");
            boolean correcto = p.matcher(txtTlfCli.getText()).matches();
            if (correcto == false) {

                Toast.makeText(this, "Formato de número de teléfono incorrecto", Toast.LENGTH_SHORT).show();
                txtTlfCli.requestFocus();
                txtTlfCli.setText(" ");
                return false;
            }
        }

        if (txtCuentaCli.getText().toString().trim().isEmpty() == true||txtTlfCli.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el número de cuenta", Toast.LENGTH_SHORT).show();
            txtCuentaCli.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\d{10}");
            boolean correcto = p.matcher(txtCuentaCli.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de número de cuenta incorrecto", Toast.LENGTH_SHORT).show();

                txtCuentaCli.requestFocus();
                txtCuentaCli.setText(" ");
                return false;
            }
        }
        return true;
    }
}