package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.ImagenDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        guardarImagenes();
        guardarAjustes();
        cambiarTamaño();
        EditText txtusuario=findViewById(R.id.txtUsuarioLog);
        EditText txtcontra=findViewById(R.id.txtContraLog);
        ImageButton ajustes=findViewById(R.id.btnImagenLog);

        Button login=findViewById(R.id.btnlogLog);
        Button guardar=findViewById(R.id.btnGuardarLog);


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

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes=new Intent(getBaseContext(), Config.class);
                startActivity(ajustes);
                finish();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guardar=new Intent(getBaseContext(), Guardar.class);
                startActivity(guardar);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos d=getDatos();
                String usuario;
                String contra;
                if(txtusuario.getText().toString().isEmpty()||txtusuario.getText().toString()==null||txtusuario.getText().toString().equals("")){
                    usuario=d.getUsuario();
                }else{
                    usuario=txtusuario.getText().toString();
                }
                if(txtcontra.getText().toString().isEmpty()||txtcontra.getText().toString()==null||txtcontra.getText().toString().equals("")){
                    contra=d.getContra();
                }else{
                    contra=txtcontra.getText().toString();
                }
                Request request = null;
                try {

                    //http://10.0.2.2:8080
                    String uri = Uri.parse(d.getUrl() + "/empleado/buscarUsuario").buildUpon()
                            .appendQueryParameter("usuario", usuario)
                            .appendQueryParameter("contraseña", contra)
                            .build().toString();
                    request = new Request.Builder()
                            .url(uri)
                            .build();
                }catch (IllegalArgumentException eae){
                    Toast.makeText(Login.this, "Formato de uri incorrecto", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpClient client=new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Error en petición", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful() && response.body()!=null ){
                            String data=response.body().string();

                            Gson gson=new Gson();
                            ObjEmpleado e=gson.fromJson(data, ObjEmpleado.class);
                            Intent gestion=new Intent(getBaseContext(), Gestion.class);
                            gestion.putExtra("usuario",e.getUsuario());
                            gestion.putExtra("rol",e.getRol());
                            startActivity(gestion);
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });
            }
        });
    }

    private datos getDatos(){
        SharedPreferences preferencias= getBaseContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        preferenceHelper helper=new preferenceHelper(preferencias);
        datos d=helper.cargar();
        return d;
    }

    private void guardarImagenes(){
        PreferenciasHelper helper=new PreferenciasHelper(getBaseContext());
        SQLiteDatabase bd=helper.getWritableDatabase();
        ImagenDAO imagenDAO=new ImagenDAO();

        try {
            InputStream stream= getAssets().open("director.jpg");

        byte[] imageBytes = new byte[stream.available()];
        DataInputStream dataInputStream = new DataInputStream(stream);
        dataInputStream.readFully(imageBytes);

            imagenDAO.insertarImagen(bd,"Director",imageBytes);
            stream= getAssets().open("empleado.png");

            imageBytes = new byte[stream.available()];
            dataInputStream = new DataInputStream(stream);
            dataInputStream.readFully(imageBytes);
            imagenDAO.insertarImagen(bd,"Inversionista",imageBytes);

            helper.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  void  guardarAjustes(){
        PreferenciasHelper helper=new PreferenciasHelper(getBaseContext());
        SQLiteDatabase bd=helper.getWritableDatabase();
        AjustesDAO ajustesDAO=new AjustesDAO();
        ajustesDAO.borrarTodosLosAjustes(bd);
        ArrayList<Ajustes> total=ajustesDAO.listarAjustes(bd);
        if(total.size()>0){
            return;
        }
        Ajustes a1=new Ajustes(10,false);
        Ajustes a2=new Ajustes(11,false);
        Ajustes a3=new Ajustes(12,false);
        Ajustes a4=new Ajustes(14,false);
        Ajustes a5=new Ajustes(16,false);
        Ajustes a6=new Ajustes(18,true);
        Ajustes a7=new Ajustes(20,false);
        total.add(a6);total.add(a1);total.add(a2);total.add(a3);total.add(a4);total.add(a5);total.add(a7);
        ajustesDAO.insertarAjustes(bd,total);
        helper.close();
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
        EditText txtusuario=findViewById(R.id.txtUsuarioLog);
        EditText txtcontra=findViewById(R.id.txtContraLog);

        TextView texto1=findViewById(R.id.textView1Log);
        TextView texto2=findViewById(R.id.textView2Log);
        Button login=findViewById(R.id.btnlogLog);
        Button guardar=findViewById(R.id.btnGuardarLog);

        txtcontra.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtusuario.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        login.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        guardar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }



}