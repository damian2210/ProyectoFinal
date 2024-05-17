package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        String usuario;
        String rol;
        String codemp;
        Intent intent = getIntent();
        Button Suc=findViewById(R.id.btnSucGest);
        Button Emp=findViewById(R.id.btnEmpGest);
        Button Cli=findViewById(R.id.btnCliGest);
        Button Pro=findViewById(R.id.btnProdGest);
        Button Prest=findViewById(R.id.btnPrestGest);
        Button Vend=findViewById(R.id.btnVendGest);

        TextView nombreUsuarioGest=findViewById(R.id.txtViewNombreUser);
        if (intent != null) {
             usuario = intent.getStringExtra("usuario");
             rol = intent.getStringExtra("rol");
            codemp = intent.getStringExtra("cod");
            nombreUsuarioGest.setText(usuario);

        }
        Suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Sucursal.class);
                startActivity(cambio);
            }
        });
        Emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Empleado.class);
                startActivity(cambio);
            }
        });

        Cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Cliente.class);
                startActivity(cambio);
            }
        });
        Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Producto.class);
                startActivity(cambio);
            }
        });
        Prest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Prestar.class);
                startActivity(cambio);
            }
        });
        Vend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambio=new Intent(v.getContext(),Vender.class);
                startActivity(cambio);
            }
        });
    }
}