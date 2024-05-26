package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;

public class Guardar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guardar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText txtContraGuar=findViewById(R.id.txtContraGuar);
        EditText txtUsuarioGuar=findViewById(R.id.txtUsuarioGuar);
        EditText txtURLGuar=findViewById(R.id.txtURLGuar);
        Button guardarGuar=findViewById(R.id.btnGuardarGuar);
        Button salirGuar=findViewById(R.id.btnSalirGuar);



        ImageButton ajustes=findViewById(R.id.btnImagenGuar);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajustes=new Intent(getBaseContext(), Config.class);
                startActivity(ajustes);
                finish();
            }
        });
        SharedPreferences preferencias= getBaseContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        preferenceHelper helper=new preferenceHelper(preferencias);
        guardarGuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtContraGuar.getText().toString().trim().equals("")){
                    Toast.makeText(Guardar.this, R.string.intContra, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtUsuarioGuar.getText().toString().trim().equals("")){
                    Toast.makeText(Guardar.this, R.string.intUser, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtURLGuar.getText().toString().trim().equals("")){
                    Toast.makeText(Guardar.this, R.string.intURL, Toast.LENGTH_SHORT).show();
                    return;
                }
                    helper.guardar(txtUsuarioGuar.getText().toString().trim(),txtContraGuar.getText().toString().trim(),txtURLGuar.getText().toString().trim());
                    Toast.makeText(Guardar.this, R.string.prefGuar, Toast.LENGTH_SHORT).show();
                }

        });
        salirGuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(getBaseContext(), Login.class);

                startActivity(login);
            }
        });
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
        EditText txtContraGuar=findViewById(R.id.txtContraGuar);
        EditText txtUsuarioGuar=findViewById(R.id.txtUsuarioGuar);
        EditText txtURLGuar=findViewById(R.id.txtURLGuar);
        Button guardarGuar=findViewById(R.id.btnGuardarGuar);
        Button salirGuar=findViewById(R.id.btnSalirGuar);
        TextView texto1=findViewById(R.id.textViewGuar);
        TextView texto2=findViewById(R.id.textView3Guar);
        TextView texto3=findViewById(R.id.textView4Guar);

        txtContraGuar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtUsuarioGuar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        txtURLGuar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        guardarGuar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        salirGuar.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto1.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
        texto3.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño);
    }
}