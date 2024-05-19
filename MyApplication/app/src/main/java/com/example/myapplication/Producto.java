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
import android.widget.AdapterView;
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

import com.example.myapplication.Objetos.ObjEmpleado;
import com.example.myapplication.Objetos.ObjProducto;
import com.example.myapplication.Objetos.ObjVender;
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

public class Producto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cambiarTamaño();
        Spinner spin=findViewById(R.id.spinner);
        String usuario = null;
        String rol = null;
        Intent intent = getIntent();
        TextView txtvFecha=findViewById(R.id.textView4Pro);
        EditText txtFechaPro=findViewById(R.id.txtFechaPro);
        EditText txtCodPro=findViewById(R.id.txtCodPro);
        EditText txtPuntPro=findViewById(R.id.txtPuntPro);
        EditText txtInteresPro=findViewById(R.id.txtInteresPro);


        OkHttpClient client=new OkHttpClient();
        Button insPro=findViewById(R.id.btnInsPro);
        Button modPro=findViewById(R.id.btnModPro);
        Button borrarPro=findViewById(R.id.btnBorrarPro);
        Button verPro=findViewById(R.id.btnVisualizarPro);
        Button salirPro=findViewById(R.id.btnSalirPro);

        ImageButton ajustes=findViewById(R.id.btnImagenPro);


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
        salirPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salir=new Intent(getBaseContext(), Gestion.class);
                salir.putExtra("usuario",usuario2);
                salir.putExtra("rol",rol2);
                startActivity(salir);
                finish();
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Financiación")){
                    txtvFecha.setEnabled(true);
                    txtFechaPro.setEnabled(true);
                }else{
                    txtvFecha.setEnabled(false);
                    txtFechaPro.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        verPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestion=new Intent(getBaseContext(), Visualizar.class);
                gestion.putExtra("clase","producto");

                startActivity(gestion);
                finish();
            }
        });
        insPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarPro(txtCodPro,txtPuntPro,txtInteresPro,txtFechaPro,spin);

                if (correcto == true) {
                    String codPro = txtCodPro.getText().toString();
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
                                    Toast.makeText(Producto.this, "Error en peticion", Toast.LENGTH_SHORT).show();
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
                                if(producto!=null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Producto.this, "El producto ya existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                if(spin.getSelectedItem().toString().trim().equals("Financiación")) {
                                    producto = new ObjProducto(txtCodPro.getText().toString(), txtPuntPro.getText().toString(), spin.getSelectedItem().toString(), Integer.parseInt(txtInteresPro.getText().toString()), Date.valueOf(txtFechaPro.getText().toString()));
                                }else {
                                    producto = new ObjProducto(txtCodPro.getText().toString(), txtPuntPro.getText().toString(), spin.getSelectedItem().toString(), Integer.parseInt(txtInteresPro.getText().toString()), null);
                                }
                                insertarPro( client,producto);
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Producto.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        modPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarPro(txtCodPro,txtPuntPro,txtInteresPro,txtFechaPro,spin);

                if (correcto == true) {
                    String codPro = txtCodPro.getText().toString();
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
                                    Toast.makeText(Producto.this, "Error en peticion", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(Producto.this, "El producto no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                if(spin.getSelectedItem().toString().trim().equals("Financiación")) {
                                    producto = new ObjProducto(txtCodPro.getText().toString(), txtPuntPro.getText().toString(), spin.getSelectedItem().toString(), Integer.parseInt(txtInteresPro.getText().toString()), Date.valueOf(txtFechaPro.getText().toString()));
                                }else {
                                    producto = new ObjProducto(txtCodPro.getText().toString(), txtPuntPro.getText().toString(), spin.getSelectedItem().toString(), Integer.parseInt(txtInteresPro.getText().toString()), null);
                                }
                                modificarPro( client,producto);
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Producto.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
        borrarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                boolean correcto = validarPro(txtCodPro,txtPuntPro,txtInteresPro,txtFechaPro,spin);

                if (correcto == true) {
                    String codPro = txtCodPro.getText().toString();
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
                                    Toast.makeText(Producto.this, "Error en peticion", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(Producto.this, "El producto no existe", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return;
                                }
                                String uri = Uri.parse(d.getUrl() + "/vender/buscarProductoEnVenta").buildUpon()
                                        .appendQueryParameter("id",txtCodPro.getText().toString())
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
                                                Toast.makeText(Producto.this, "Error en petición", Toast.LENGTH_SHORT).show();
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
                                                        Toast.makeText(Producto.this, "El producto esta en una venta,no se puede borrar", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }


                                            borrarPro( client,producto);

                                        }else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Producto.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });



                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Producto.this, "Error en respuesta ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });

    }

    public void insertarPro( OkHttpClient client,ObjProducto producto){
        datos d=getDatos();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        String json = gson.toJson(producto);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/producto/insertar").buildUpon()
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
                        Toast.makeText(Producto.this, "No insertado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Producto.this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Producto.this, "No insertado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void modificarPro( OkHttpClient client,ObjProducto producto){
        datos d=getDatos();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        String json = gson.toJson(producto);

        RequestBody body=RequestBody.create(MediaType.parse("application/json"),json);

        String uri = Uri.parse(d.getUrl() + "/producto/modificar").buildUpon()
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
                        Toast.makeText(Producto.this, "No modificado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Producto.this, "Modificado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Producto.this, "No modificado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public void borrarPro( OkHttpClient client,ObjProducto producto){
        datos d=getDatos();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        String json = gson.toJson(producto);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        String uri = Uri.parse(d.getUrl() + "/producto/borrar").buildUpon()
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
                        Toast.makeText(Producto.this, "No borrado,error en peticion", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Producto.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Producto.this, "No borrado,error en respuesta", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    public boolean validarPro(EditText txtCodPro,EditText txtPuntPro,EditText txtInteresPro, EditText txtFechaPro,Spinner spin) {
        if (txtCodPro.getText().toString().isEmpty() == true||txtCodPro.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el código", Toast.LENGTH_SHORT).show();
            txtCodPro.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^p\\d{2}$");
            boolean correcto = p.matcher(txtCodPro.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de código incorrecto(Formato válido:p+dos dígitos)", Toast.LENGTH_SHORT).show();
                txtCodPro.requestFocus();
                txtCodPro.setText(" ");
                return false;
            }
        }
        if (txtInteresPro.getText().toString().isEmpty() == true||txtInteresPro.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir el interés", Toast.LENGTH_SHORT).show();
            txtInteresPro.requestFocus();
            return false;
        } else {
            for (int i = 0; i < txtInteresPro.getText().length(); i++) {
                if (Character.isLetter(txtInteresPro.getText().charAt(i))) {
                    Toast.makeText(this, "El intéres solo pueden ser números", Toast.LENGTH_SHORT).show();
                    txtInteresPro.requestFocus();
                    txtInteresPro.setText(" ");
                    return false;
                }
            }
        }
        if (txtPuntPro.getText().toString().isEmpty() == true||txtPuntPro.getText().toString()==null) {

            Toast.makeText(this, "Tiene que introducir la puntuación", Toast.LENGTH_SHORT).show();
            txtPuntPro.requestFocus();
            return false;
        } else {
            Pattern p = Pattern.compile("^\\p{L}{3}");
            boolean correcto = p.matcher(txtPuntPro.getText()).matches();
            if (correcto == false) {
                Toast.makeText(this, "Formato de puntuación incorrecto(Formato correcto:tres letras)", Toast.LENGTH_SHORT).show();
                txtPuntPro.requestFocus();
                txtPuntPro.setText(" ");
                return false;
            }
        }
        if (spin.getSelectedItem().toString().equals("Financiación")) {
            if (txtFechaPro.getText().toString().isEmpty() == true||txtFechaPro.getText().toString()==null) {
                Toast.makeText(this, "Tiene que introducir la fecha de devolución", Toast.LENGTH_SHORT).show();

                txtFechaPro.requestFocus();
                return false;
            } else {
                try {
                    Date fecha = Date.valueOf(txtFechaPro.getText().toString());

                } catch (IllegalArgumentException iae) {
                    Toast.makeText(this, "Formato de fecha incorrecto(Formato correcto:yyyy-mm-dd)", Toast.LENGTH_SHORT).show();
                    txtFechaPro.requestFocus();
                    txtFechaPro.setText(" ");
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
    private void cambiarTamaño(){
        int tamaño=getTamaño();

        TextView txtvFecha=findViewById(R.id.textView4Pro);
        EditText txtFechaPro=findViewById(R.id.txtFechaPro);
        EditText txtCodPro=findViewById(R.id.txtCodPro);
        EditText txtPuntPro=findViewById(R.id.txtPuntPro);
        EditText txtInteresPro=findViewById(R.id.txtInteresPro);



        Button insPro=findViewById(R.id.btnInsPro);
        Button modPro=findViewById(R.id.btnModPro);
        Button borrarPro=findViewById(R.id.btnBorrarPro);
        Button verPro=findViewById(R.id.btnVisualizarPro);
        Button salirPro=findViewById(R.id.btnSalirPro);
        TextView texto1=findViewById(R.id.textViewPro);
        TextView texto2=findViewById(R.id.textView3Pro);
        TextView texto3=findViewById(R.id.textView4Pro);
        TextView texto4=findViewById(R.id.textView5Pro);
        TextView texto5=findViewById(R.id.textView6Pro);


        txtvFecha.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtFechaPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtCodPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtPuntPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtInteresPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto4.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto5.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        insPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        modPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        borrarPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        verPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirPro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }
}