package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.ImagenDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Gestion extends AppCompatActivity {

    Intent cambio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cambiarTamaño();
        String usuario = null;
        String rol = null;

        Intent intent = getIntent();
        Button Suc=findViewById(R.id.btnSucGest);
        Button Emp=findViewById(R.id.btnEmpGest);
        Button Cli=findViewById(R.id.btnCliGest);
        Button Pro=findViewById(R.id.btnProdGest);
        Button Prest=findViewById(R.id.btnPrestGest);
        Button Vend=findViewById(R.id.btnVendGest);


        ImageButton ajustes=findViewById(R.id.btnImagenGest);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes=new Intent(getBaseContext(), Config.class);
                startActivity(ajustes);
                finish();
            }
        });

        TextView nombreUsuarioGest=findViewById(R.id.txtViewNombreUser);
        if (intent != null) {
             usuario = intent.getStringExtra("usuario");
             rol = intent.getStringExtra("rol");

             cambiarImagen(rol);
            nombreUsuarioGest.setText(usuario);

        }
        String usuario2=usuario;
        String rol2=rol;
        Suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rol2.equals("Inversionista")){
                    Toast.makeText(Gestion.this, "Solo los directores pueden acceder a las sucursales", Toast.LENGTH_SHORT).show();
                }else{
                    cambio=new Intent(v.getContext(),Sucursal.class);
                    cambio.putExtra("usuario",usuario2);
                    cambio.putExtra("rol",rol2);
                    startActivity(cambio);
                }

            }
        });
        Emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rol2.equals("Inversionista")){
                    Toast.makeText(Gestion.this, "Solo los directores pueden acceder a los empleados", Toast.LENGTH_SHORT).show();
                }else {
                    cambio = new Intent(v.getContext(), Empleado.class);
                    cambio.putExtra("usuario", usuario2);
                    cambio.putExtra("rol", rol2);
                    startActivity(cambio);
                }
            }
        });

        Cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Cliente.class);
                cambio.putExtra("usuario",usuario2);
                cambio.putExtra("rol",rol2);
                startActivity(cambio);
            }
        });
        Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Producto.class);
                cambio.putExtra("usuario",usuario2);
                cambio.putExtra("rol",rol2);
                startActivity(cambio);
            }
        });
        Prest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rol2.equals("Inversionista")){
                    Toast.makeText(Gestion.this, "Solo los directores pueden acceder a los préstamos", Toast.LENGTH_SHORT).show();
                }else {
                    cambio = new Intent(v.getContext(), Prestar.class);
                    cambio.putExtra("usuario", usuario2);
                    cambio.putExtra("rol", rol2);
                    startActivity(cambio);
                }
            }
        });
        Vend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Vender.class);
                cambio.putExtra("usuario",usuario2);
                cambio.putExtra("rol",rol2);
                startActivity(cambio);
            }
        });
    }

    private void cambiarImagen(String rol){
        PreferenciasHelper helper=new PreferenciasHelper(getBaseContext());
        SQLiteDatabase bd=helper.getWritableDatabase();
        ImagenDAO imagenDAO=new ImagenDAO();
        ImageView imagen=findViewById(R.id.imageView);
            byte[] imageBytes= imagenDAO.obtenerImagenPorRol(bd,rol);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imagen.setImageBitmap(bitmap);
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

        Button Suc=findViewById(R.id.btnSucGest);
        Button Emp=findViewById(R.id.btnEmpGest);
        Button Cli=findViewById(R.id.btnCliGest);
        Button Pro=findViewById(R.id.btnProdGest);
        Button Prest=findViewById(R.id.btnPrestGest);
        Button Vend=findViewById(R.id.btnVendGest);
        TextView texto1=findViewById(R.id.textView6Gest);

        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        Suc.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        Emp.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        Cli.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        Pro.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        Prest.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        Vend.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);

    }

}