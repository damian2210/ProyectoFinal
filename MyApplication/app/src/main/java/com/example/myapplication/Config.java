package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bbdd.AjustesDAO;
import com.example.myapplication.bbdd.PreferenciasHelper;
import com.example.myapplication.bbdd.entidades.Ajustes;

import java.util.ArrayList;
import java.util.Locale;


public class Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button salir=findViewById(R.id.btnSalirAjustes);
        Spinner tamaños=findViewById(R.id.spinAjustes);
        Spinner idiomas=findViewById(R.id.spinIdioma);
        TextView texto=findViewById(R.id.textView8Ajus);
        TextView texto2=findViewById(R.id.textView9Ajus);
        SharedPreferences preferencias= getBaseContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        preferenceHelper helper=new preferenceHelper(preferencias);

        ArrayList<String> listaTamaños=conseguirListaTamaños();
        // Crear un adaptador utilizando el array de strings como origen de datos
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTamaños);

        // Especificar el diseño a utilizar cuando se despliega el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        tamaños.setAdapter(adapter);
        final int[] tamaño = new int[1];
        tamaños.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tamaño[0] =Integer.parseInt(parent.getItemAtPosition(position).toString().trim());
                texto.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño[0]);
                texto2.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño[0]);
                salir.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamaño[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTamaño(tamaño[0]);
                Intent login=new Intent(getBaseContext(), Login.class);
                startActivity(login);
                finish();
            }
        });

    idiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String seleccionado=parent.getItemAtPosition(position).toString();
            if(seleccionado.equals("Castellano")){
                setLocal(Config.this,"en");
                helper.guardarIdioma(seleccionado);
                finish();
                startActivity(getIntent());
            }else if(seleccionado.equals("Gallego")){
                setLocal(Config.this,"gl");
                helper.guardarIdioma(seleccionado);
                finish();
                startActivity(getIntent());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    }

    private void setLocal(Activity actividad,String idioma){
        Locale locale=new Locale(idioma);
        Locale.setDefault(locale);
        Resources resources=actividad.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    private  ArrayList<String> conseguirListaTamaños(){
        PreferenciasHelper helper=new PreferenciasHelper(getBaseContext());
        SQLiteDatabase bd=helper.getWritableDatabase();
        AjustesDAO ajustesDAO=new AjustesDAO();
        ArrayList<Ajustes> lista=ajustesDAO.listarAjustes(bd);
        ArrayList<String> listaString=new ArrayList<>();
        for (Ajustes ajuste : lista) {
            listaString.add(ajuste.getTamaño()+"");
        }
        helper.close();
        return listaString;
    }
    private void guardarTamaño(int tamaño){

        PreferenciasHelper helper=new PreferenciasHelper(getBaseContext());
        SQLiteDatabase bd=helper.getWritableDatabase();
        AjustesDAO ajustesDAO=new AjustesDAO();
        ajustesDAO.deseleccionar(bd);
        ajustesDAO.seleccionarPorTamaño(bd,tamaño);
        helper.close();
    }

    private String getIdioma(){
        SharedPreferences preferencias= getBaseContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        preferenceHelper helper=new preferenceHelper(preferencias);
        datos d=helper.cargar();
        return d.getIdioma();

    }
}